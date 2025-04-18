package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CambiarContrasenaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasena)

        // Ajuste de barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Flecha para volver atrás
        val flechaAtras = findViewById<ImageView>(R.id.flechaAtras)
        flechaAtras.setOnClickListener {
            finish()
        }

        // Conexión de los campos y botón
        val inputActual = findViewById<EditText>(R.id.inputActual)
        val inputNueva = findViewById<EditText>(R.id.inputNueva)
        val inputConfirmar = findViewById<EditText>(R.id.inputConfirmar)
        val btnCambiar = findViewById<Button>(R.id.btnCambiar)

        btnCambiar.setOnClickListener {
            val actual = inputActual.text.toString().trim()
            val nueva = inputNueva.text.toString().trim()
            val confirmar = inputConfirmar.text.toString().trim()

            // Validación de campos vacíos
            if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de coincidencia
            if (nueva != confirmar) {
                Toast.makeText(this, "La nueva contraseña no coincide con la confirmación", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Aquí podrías conectar con Firebase si deseas
            Toast.makeText(this, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()

            // Opcional: volver a pantalla anterior o ir a confirmación
            startActivity(Intent(this, ConfirmacionAjustes::class.java))
            finish()
        }
    }
}
