package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase

class PreRegistroDeVisitantes : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var documento: EditText
    private lateinit var telefono: EditText
    private lateinit var placa: EditText
    private lateinit var torre: EditText
    private lateinit var apto: EditText
    private lateinit var btnRegistrar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_registro_de_visitantes)

        // Enlazar vistas
        nombre = findViewById(R.id.editTextNombre)
        documento = findViewById(R.id.editTextDocumento)
        telefono = findViewById(R.id.editTextTelefono)
        placa = findViewById(R.id.editTextPlaca)
        torre = findViewById(R.id.editTextTorre)
        apto = findViewById(R.id.editTextApto)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            Toast.makeText(this, "Click detectado", Toast.LENGTH_SHORT).show()

            val nombreVal = nombre.text.toString().trim()
            val documentoVal = documento.text.toString().trim()
            val telefonoVal = telefono.text.toString().trim()
            val placaVal = placa.text.toString().trim()
            val torreVal = torre.text.toString().trim()
            val aptoVal = apto.text.toString().trim()

            if (nombreVal.isEmpty() || documentoVal.isEmpty() || telefonoVal.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos obligatorios.", Toast.LENGTH_SHORT).show()
            } else {
                val visitante = Visitante(
                    nombre = nombreVal,
                    documento = documentoVal,
                    telefono = telefonoVal,
                    placa = placaVal,
                    torre = torreVal,
                    apto = aptoVal
                )

                val db = FirebaseDatabase.getInstance()
                val ref = db.getReference("visitantes")
                val id = ref.push().key

                if (id != null) {
                    ref.child(id).setValue(visitante).addOnSuccessListener {
                        Toast.makeText(this, "Visitante registrado correctamente", Toast.LENGTH_SHORT).show()

                        nombre.text.clear()
                        documento.text.clear()
                        telefono.text.clear()
                        placa.text.clear()
                        torre.text.clear()
                        apto.text.clear()

                        val intent = Intent(this, ConfirmacionRegistroActivity::class.java)
                        startActivity(intent)
                    }.addOnFailureListener {
                        Toast.makeText(this, "Error al registrar en Firebase", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

// Modelo interno
data class Visitante(
    val nombre: String = "",
    val documento: String = "",
    val telefono: String = "",
    val placa: String = "",
    val torre: String = "",
    val apto: String = ""
)
