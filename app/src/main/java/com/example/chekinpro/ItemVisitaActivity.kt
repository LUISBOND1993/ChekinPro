package com.example.chekinpro

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class ItemVisitaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VisitasFinalizadasAdapter
    private val visitas = mutableListOf<VisitaFinalizada>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_visita)

        recyclerView = findViewById(R.id.recyclerVisitas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VisitasFinalizadasAdapter(visitas)
        recyclerView.adapter = adapter

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
            set(Calendar.MILLISECOND, 0)
        }.time

        val fechaFin = Calendar.getInstance().apply {
            time = sdf.parse(fechaFinStr) ?: Date()
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time

        cargarVisitas("pre_registro_visitantes", "PreRegistro", fechaInicio, fechaFin)
        cargarVisitas("visitante_rapido", "Rapido", fechaInicio, fechaFin)
    }

    private fun cargarVisitas(
        coleccion: String,
        tipo: String,
        desde: Date,
        hasta: Date
    ) {
        db.collection(coleccion)
            .whereEqualTo("estado", "Finalizado")
            .whereGreaterThanOrEqualTo("fechaRegistro", desde)
            .whereLessThanOrEqualTo("fechaRegistro", hasta)
            .orderBy("fechaRegistro", Query.Direction.DESCENDING)
            .get()
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
                        apto = doc.getString("apto") ?: "",
                        fechaRegistro = fechaIngreso,
                        fechaSalida = fechaSalida,
                        tipo = tipo,
                        totalMinutos = duracionMin,
                        totalPago = total
                    )
                    visitas.add(visita)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al consultar $coleccion", Toast.LENGTH_SHORT).show()
            }
    }
}
