package com.example.chekinpro

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class PanelCobrosActivity : AppCompatActivity() {

    private lateinit var txtFechaIngreso: TextView
    private lateinit var txtFechaSalida: TextView
    private lateinit var txtDuracion: TextView
    private lateinit var txtTotal: TextView
    private lateinit var btnConfirmarSalida: Button

    private lateinit var db: FirebaseFirestore
    private var visitanteId: String? = null
    private var tipo: String? = null
    private var fechaIngreso: Timestamp? = null
    private var coleccion: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.panel_cobros)

        txtFechaIngreso = findViewById(R.id.valorFechaIngreso)
        txtFechaSalida = findViewById(R.id.valorFechaSalida)
        txtDuracion = findViewById(R.id.valorDuracion)
        txtTotal = findViewById(R.id.valorTotal)
        btnConfirmarSalida = findViewById(R.id.btnConfirmarSalida)

        db = FirebaseFirestore.getInstance()

        visitanteId = intent.getStringExtra("id")
        tipo = intent.getStringExtra("tipo")

        coleccion = when (tipo) {
            "PreRegistro" -> "pre_registro_visitantes"
            "Rapido" -> "visitante_rapido"
            else -> ""
        }

        if (visitanteId != null && coleccion.isNotEmpty()) {
            cargarDatosVisitante()
        } else {
            Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnConfirmarSalida.setOnClickListener {
            confirmarSalida()
        }
    }

    private fun cargarDatosVisitante() {
        db.collection(coleccion).document(visitanteId!!)
            .get()
            .addOnSuccessListener { doc ->
                fechaIngreso = doc.getTimestamp("fechaRegistro")

                val fechaSalida = Timestamp.now()

                // Mostrar fechas
                val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())
                val ingresoTexto = sdf.format(fechaIngreso?.toDate())
                val salidaTexto = sdf.format(fechaSalida.toDate())

                txtFechaIngreso.text = ingresoTexto
                txtFechaSalida.text = salidaTexto

                // Calcular duración
                val duracionMin = calcularDuracionMinutos(fechaIngreso!!, fechaSalida)
                txtDuracion.text = duracionMin.toString()

                // Calcular total
                val total = duracionMin * 30
                txtTotal.text = "$${total} COP"

            }.addOnFailureListener {
                Toast.makeText(this, "Error al cargar visitante", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    private fun calcularDuracionMinutos(inicio: Timestamp, fin: Timestamp): Long {
        val diffMillis = fin.toDate().time - inicio.toDate().time
        return diffMillis / 60000
    }

    private fun confirmarSalida() {
        val fechaSalida = com.google.firebase.Timestamp.now()

        val datosActualizados = mapOf(
            "estado" to "Finalizado",
            "fechaSalida" to fechaSalida
        )

        db.collection(coleccion).document(visitanteId!!)
            .update(datosActualizados)
            .addOnSuccessListener {
                Toast.makeText(this, "Salida registrada exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al registrar salida", Toast.LENGTH_SHORT).show()
            }
    }

}
