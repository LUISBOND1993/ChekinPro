package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PanelCobrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.panel_cobros)

        // Flecha para regresar
        val btnRetroceso = findViewById<ImageView>(R.id.retroceso)
        btnRetroceso.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }

        // Botón "Registrar Salida"
        val btnRegistrarSalida = findViewById<Button>(R.id.btnRegistrarSalida)
        btnRegistrarSalida.setOnClickListener {
            val intent = Intent(this, FacturacionYCobroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
