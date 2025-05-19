package com.example.chekinpro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class ItemVisitaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VisitasFinalizadasAdapter
    private lateinit var btnExportar: Button
    private val visitas = mutableListOf<VisitaFinalizada>()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val REQUEST_PERMISSION_WRITE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_visita)

        recyclerView = findViewById(R.id.recyclerVisitas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VisitasFinalizadasAdapter(visitas)
        recyclerView.adapter = adapter

        btnExportar = findViewById(R.id.btnExportarCSV)
        btnExportar.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_WRITE
                )
            } else {
                exportarCSV()
            }
        }

        val fechaInicioStr = intent.getStringExtra("fechaInicio") ?: ""
        val fechaFinStr = intent.getStringExtra("fechaFin") ?: ""

        if (fechaInicioStr.isEmpty() || fechaFinStr.isEmpty()) {
            Toast.makeText(this, "Fechas no válidas", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaInicio = Calendar.getInstance().apply {
            time = sdf.parse(fechaInicioStr) ?: Date()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.time

        val fechaFin = Calendar.getInstance().apply {
            time = sdf.parse(fechaFinStr) ?: Date()
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.time

        val uid = auth.currentUser?.uid

        if (uid == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        db.collection("usuarios").document(uid).get()
            .addOnSuccessListener { doc ->
                val tipoUsuario = doc.getString("tipoUsuario") ?: ""
                if (tipoUsuario != "Residente") {
                    Toast.makeText(this, "Acceso denegado: solo para residentes", Toast.LENGTH_LONG).show()
                    finish()
                    return@addOnSuccessListener
                }

                val torre = doc.getString("torre") ?: ""
                val apartamento = doc.getString("apartamento") ?: ""
                val conjunto = doc.getString("conjunto") ?: ""

                if (torre.isEmpty() || apartamento.isEmpty() || conjunto.isEmpty()) {
                    Toast.makeText(this, "Información del usuario incompleta", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                // Asegura que ambas colecciones se cargan correctamente
                cargarVisitas("pre_registro_visitantes", "PreRegistro", fechaInicio, fechaFin, torre, apartamento, conjunto)
                cargarVisitas("visitante_rapido", "Rapido", fechaInicio, fechaFin, torre, apartamento, conjunto)
            }
            .addOnFailureListener {
                Toast.makeText(this, "No se pudo obtener datos del usuario", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    private fun cargarVisitas(
        coleccion: String,
        tipo: String,
        desde: Date,
        hasta: Date,
        torre: String,
        apartamento: String,
        conjunto: String
    ) {
        val query = db.collection(coleccion)
            .whereEqualTo("estado", "Finalizado")
            .whereEqualTo("torre", torre)
            .whereEqualTo("conjunto", conjunto)
            .whereGreaterThanOrEqualTo("fechaRegistro", desde)
            .whereLessThanOrEqualTo("fechaRegistro", hasta)
            .orderBy("fechaRegistro", Query.Direction.DESCENDING)

        // Ajustar según el nombre del campo correcto
        val finalQuery = if (coleccion == "pre_registro_visitantes") {
            query.whereEqualTo("apto", apartamento)
        } else {
            query.whereEqualTo("apartamento", apartamento)
        }

        finalQuery.get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val fechaIngreso = doc.getDate("fechaRegistro") ?: Date()
                    val fechaSalida = doc.getDate("fechaSalida") ?: Date()
                    val duracionMin = (fechaSalida.time - fechaIngreso.time) / 60000
                    val total = duracionMin * 30

                    val visita = VisitaFinalizada(
                        id = doc.id,
                        nombre = doc.getString("nombre") ?: "",
                        placa = doc.getString("placa") ?: "",
                        torre = doc.getString("torre") ?: "",
                        apto = doc.getString("apto") ?: doc.getString("apartamento") ?: "",
                        fechaRegistro = fechaIngreso,
                        fechaSalida = fechaSalida,
                        tipo = tipo,
                        totalMinutos = duracionMin,
                        totalPago = total,
                        conjunto = doc.getString("conjunto") ?: ""
                    )
                    visitas.add(visita)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al consultar $coleccion", Toast.LENGTH_SHORT).show()
            }
    }


    private fun exportarCSV() {
        if (visitas.isEmpty()) {
            Toast.makeText(this, "No hay datos para exportar", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "historial_visitas_$timestamp.csv"
            val file = File(downloadsDir, fileName)
            val writer = FileWriter(file)

            writer.append("Nombre,Placa,Torre,Apto,Conjunto,Fecha Ingreso,Fecha Salida,Duración (min),Total (COP)\n")
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

            for (v in visitas) {
                writer.append("${v.nombre},${v.placa},${v.torre},${v.apto},${v.conjunto},${sdf.format(v.fechaRegistro)},${sdf.format(v.fechaSalida)},${v.totalMinutos},${v.totalPago}\n")
            }

            writer.flush()
            writer.close()

            Toast.makeText(this, "Archivo guardado en Descargas", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error exportando CSV: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_WRITE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            exportarCSV()
        } else {
            Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show()
        }
    }
}
