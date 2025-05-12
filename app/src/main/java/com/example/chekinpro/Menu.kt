package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Conexión del botón de Pre-Registro
        val btnPreRegistro = findViewById<LinearLayout>(R.id.btnPreRegistro)
        btnPreRegistro.setOnClickListener {
            val intent = Intent(this, PreRegistroDeVisitantes::class.java)
            startActivity(intent)
        }

        // Conexión del botón de Registro Rápido
        val btnRegistroRapido = findViewById<LinearLayout>(R.id.btnRegistroRapido)
        btnRegistroRapido.setOnClickListener {
            val intent = Intent(this, RegistroDeVisitantes::class.java)
            startActivity(intent)
        }

        // Conexión del botón de Historial de Visitas y Cobros
        val btnHistorial = findViewById<LinearLayout>(R.id.btnHistorial)
        btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialDeVisitas::class.java)
            startActivity(intent)
        }

        // Conexión del botón Panel de Cobros (corregido aquí)
        val btnPanelCobros = findViewById<LinearLayout>(R.id.btnPanelCobros)
        btnPanelCobros.setOnClickListener {
            val intent = Intent(this, ListaSolicitudesActivity::class.java)
            startActivity(intent)
        }

        // Conexión del botón Configuración de Cuenta
        val btnConfiguracion = findViewById<LinearLayout>(R.id.btnConfiguracion)
        btnConfiguracion.setOnClickListener {
            val intent = Intent(this, ConfiguracionCuentaActivity::class.java)
            startActivity(intent)
        }
    }
}
