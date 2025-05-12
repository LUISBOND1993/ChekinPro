package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ListaSolicitudesActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var db: FirebaseFirestore
    private val listaSolicitudes = mutableListOf<VisitanteEnCurso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_solicitudes)

        recycler = findViewById(R.id.recyclerSolicitudes)
        recycler.layoutManager = LinearLayoutManager(this)
        db = FirebaseFirestore.getInstance()

        obtenerSolicitudes()
    }

    private fun obtenerSolicitudes() {
        listaSolicitudes.clear()

        // Obtener desde "pre-registro-visitantes"
        db.collection("pre-registro-visitantes")
            .whereEqualTo("estado", "En curso")
            .get()
            .addOnSuccessListener { documentos1 ->
                for (doc in documentos1) {
                    listaSolicitudes.add(
                        VisitanteEnCurso(
                            id = doc.id,
                            nombre = doc.getString("nombre") ?: "",
                            placa = doc.getString("placa") ?: "",
                            tipo = "PreRegistro",
                            fechaRegistro = doc.getTimestamp("fechaRegistro")
                        )
                    )
                }

                // Obtener desde "visitante_rapido"
                db.collection("visitante_rapido")
                    .whereEqualTo("estado", "En curso")
                    .get()
                    .addOnSuccessListener { documentos2 ->
                        for (doc in documentos2) {
                            listaSolicitudes.add(
                                VisitanteEnCurso(
                                    id = doc.id,
                                    nombre = doc.getString("nombre") ?: "",
                                    placa = doc.getString("placa") ?: "",
                                    tipo = "Rapido",
                                    fechaRegistro = doc.getTimestamp("fechaRegistro")
                                )
                            )
                        }

                        if (listaSolicitudes.isEmpty()) {
                            Toast.makeText(this, "No hay visitantes en curso.", Toast.LENGTH_SHORT).show()
                        }

                        recycler.adapter = SolicitudesAdapter(listaSolicitudes) { visitante ->
                            val intent = Intent(this, PanelCobrosActivity::class.java)
                            intent.putExtra("id", visitante.id)
                            intent.putExtra("tipo", visitante.tipo)
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al obtener visitantes rápidos.", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al obtener preregistros.", Toast.LENGTH_SHORT).show()
            }
    }
}
