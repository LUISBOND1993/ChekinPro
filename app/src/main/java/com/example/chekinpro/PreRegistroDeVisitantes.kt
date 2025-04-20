package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PreRegistroDeVisitantes : AppCompatActivity() {

    // Declaración de campos
    private lateinit var nombre: EditText
    private lateinit var documento: EditText
    private lateinit var telefono: EditText
    private lateinit var placa: EditText
    private lateinit var torre: EditText
    private lateinit var apto: EditText
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pre_registro_de_visitantes)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Conexión de los elementos del formulario
        nombre = findViewById(R.id.editTextNombre)
        documento = findViewById(R.id.editTextDocumento)
        telefono = findViewById(R.id.editTextTelefono)
        placa = findViewById(R.id.editTextPlaca)
        torre = findViewById(R.id.editTextTorre)
        apto = findViewById(R.id.editTextApto)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        // Lógica del botón
        btnRegistrar.setOnClickListener {
            val nombreVal = nombre.text.toString().trim()
            val documentoVal = documento.text.toString().trim()
            val telefonoVal = telefono.text.toString().trim()
            val placaVal = placa.text.toString().trim()
            val torreVal = torre.text.toString().trim()
            val aptoVal = apto.text.toString().trim()

            // Validación simple de campos obligatorios
            if (nombreVal.isEmpty() || documentoVal.isEmpty() || telefonoVal.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos obligatorios.", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí más adelante se podrá enviar a Firebase
                val intent = Intent(this, ConfirmacionRegistroActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

