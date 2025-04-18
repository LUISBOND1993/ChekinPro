package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConfiguracionCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion_cuenta)

        // Ajuste para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Flecha de retroceso al menú
        val flechaAtras = findViewById<ImageView>(R.id.flechaAtras)
        flechaAtras.setOnClickListener {
            startActivity(Intent(this, Menu::class.java))
            finish()
        }

        // Botón CAMBIAR CONTRASEÑA
        val btnCambiarContrasena = findViewById<Button>(R.id.btnCambiarContrasena)
        btnCambiarContrasena.setOnClickListener {
            startActivity(Intent(this, CambiarContrasenaActivity::class.java))
        }

        // Campos a validar
        val inputTelefono = findViewById<EditText>(R.id.inputTelefono)
        val inputPlaca = findViewById<EditText>(R.id.inputPlaca)
        val inputTorre = findViewById<EditText>(R.id.inputTorre)
        val inputApto = findViewById<EditText>(R.id.inputApto)

        // Botón GUARDAR CAMBIOS
        val btnGuardarCambios = findViewById<Button>(R.id.btnGuardarCambios)
        btnGuardarCambios.setOnClickListener {
            val telefono = inputTelefono.text.toString().trim()
            val placa = inputPlaca.text.toString().trim()
            val torre = inputTorre.text.toString().trim()
            val apto = inputApto.text.toString().trim()

            // Validación de campos obligatorios
            if (telefono.isEmpty() || placa.isEmpty() || torre.isEmpty() || apto.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos antes de continuar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            startActivity(Intent(this, ConfirmacionAjustes::class.java))

            // Después de 60 segundos, volver al menú
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, Menu::class.java))
                finish()
            }, 60000)
        }
    }
}

