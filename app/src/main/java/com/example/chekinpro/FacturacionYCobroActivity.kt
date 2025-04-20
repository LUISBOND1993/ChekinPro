package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FacturacionYCobroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facturacion_ycobro)

        // Flecha de retroceso que vuelve al menú
        val flecha = findViewById<ImageView>(R.id.retroceso)
        flecha.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }

        // Botón check que lleva a ConfirmacionCobroActivity
        val botonCheck = findViewById<ImageView>(R.id.icono_check)
        botonCheck.setOnClickListener {
            val intent = Intent(this, ConfirmacionCobroActivity::class.java)
            startActivity(intent)
        }
    }
}
