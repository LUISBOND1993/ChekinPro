package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class PreRegistroDeVisitantes : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var documento: EditText
    private lateinit var telefono: EditText
    private lateinit var placa: EditText
    private lateinit var torre: EditText
    private lateinit var apto: EditText
    private lateinit var btnRegistrar: MaterialButton
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_registro_de_visitantes)

        nombre = findViewById(R.id.editTextNombre)
        documento = findViewById(R.id.editTextDocumento)
        telefono = findViewById(R.id.editTextTelefono)
        placa = findViewById(R.id.editTextPlaca)
        torre = findViewById(R.id.editTextTorre)
        apto = findViewById(R.id.editTextApto)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        firestore = FirebaseFirestore.getInstance()

        btnRegistrar.setOnClickListener {
            val nombreVal = nombre.text.toString().trim()
            val documentoVal = documento.text.toString().trim()
            val telefonoVal = telefono.text.toString().trim()
            val placaVal = placa.text.toString().trim()
            val torreVal = torre.text.toString().trim()
            val aptoVal = apto.text.toString().trim()

            if (nombreVal.isEmpty() || documentoVal.isEmpty() || telefonoVal.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos obligatorios.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val visitante = hashMapOf(
                "nombre" to nombreVal,
                "documento" to documentoVal,
                "telefono" to telefonoVal,
                "placa" to placaVal,
                "torre" to torreVal,
                "apto" to aptoVal,
                "fechaRegistro" to Timestamp.now(),
                "estado" to "En curso"
            )

            firestore.collection("pre_registro_visitantes")
                .add(visitante)
                .addOnSuccessListener {
                    Toast.makeText(this, "Preregistro exitoso", Toast.LENGTH_SHORT).show()

                    nombre.text.clear()
                    documento.text.clear()
                    telefono.text.clear()
                    placa.text.clear()
                    torre.text.clear()
                    apto.text.clear()

                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al registrar en Firestore", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
