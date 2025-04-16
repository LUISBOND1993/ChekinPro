package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
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
        val btnCambiarContrasena = findViewById<Button>(R.id.btn_cambiar_contraseña)
        btnCambiarContrasena.setOnClickListener {
            startActivity(Intent(this, CambiarContrasenaActivity::class.java))
        }

        // Botón GUARDAR CAMBIOS con delay hacia el menú
        val btnGuardarCambios = findViewById<Button>(R.id.btnRegistrar)
        btnGuardarCambios.setOnClickListener {
            // Ir a pantalla de confirmación de ajustes
            startActivity(Intent(this, ConfirmacionAjustes::class.java))

            // Delay para regresar al menú después de 60 segundos
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, Menu::class.java))
                finish()
            }, 60000) // 60 segundos = 60000 milisegundos
        }
    }
}

