package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Olvidaste_contrasenia_two : AppCompatActivity() {

    private lateinit var inputCodigo: EditText
    private lateinit var btnValidarCodigo: Button
    private var codigoGenerado: String = ""
    private var correoUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olvidaste_contrania_two)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias UI
        inputCodigo = findViewById(R.id.inputCodigo_)
        btnValidarCodigo = findViewById(R.id.btnRegistrarCod)

        // Recibir extras
        correoUsuario = intent.getStringExtra("email") ?: ""
        codigoGenerado = intent.getStringExtra("codigo") ?: ""

        // Validar código
        btnValidarCodigo.setOnClickListener {
            val codigoIngresado = inputCodigo.text.toString().trim()

            if (codigoIngresado.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa el código", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (codigoIngresado == codigoGenerado) {
                // Código correcto → continuar al paso 3
                val intent = Intent(this, Olvidaste_contrasenia_three::class.java)
                intent.putExtra("email", correoUsuario)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Código incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
