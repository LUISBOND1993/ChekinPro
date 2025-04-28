package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PanelCobrosActivity : AppCompatActivity() {

    private var visitanteSeleccionado: String? = null
    private var placaSeleccionada: String? = null
    private var cobroSeleccionado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.panel_cobros)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRetroceso = findViewById<ImageView>(R.id.retroceso)
        btnRetroceso.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }

        val tarjeta1 = findViewById<LinearLayout>(R.id.tarjeta1)
        val tarjeta2 = findViewById<LinearLayout>(R.id.tarjeta2)
        val tarjeta3 = findViewById<LinearLayout>(R.id.tarjeta3)

        tarjeta1.setOnClickListener {
            visitanteSeleccionado = "Ricardo Suarez Bonilla"
            placaSeleccionada = "RKL732"
            cobroSeleccionado = "$3.600"
            Toast.makeText(this, "Seleccionaste visitante 1", Toast.LENGTH_SHORT).show()
        }

        tarjeta2.setOnClickListener {
            visitanteSeleccionado = "Ricardo Suarez Bonilla"
            placaSeleccionada = "RKL732"
            cobroSeleccionado = "$5.600"
            Toast.makeText(this, "Seleccionaste visitante 2", Toast.LENGTH_SHORT).show()
        }

        tarjeta3.setOnClickListener {
            visitanteSeleccionado = "Ricardo Suarez Bonilla"
            placaSeleccionada = "RKL732"
            cobroSeleccionado = "$5.600"
            Toast.makeText(this, "Seleccionaste visitante 3", Toast.LENGTH_SHORT).show()
        }

        val btnRegistrarSalida = findViewById<Button>(R.id.btnRegistrarSalida)
        btnRegistrarSalida.setOnClickListener {
            if (visitanteSeleccionado != null) {
                val intent = Intent(this, FacturacionYCobroActivity::class.java)
                intent.putExtra("visitante", visitanteSeleccionado)
                intent.putExtra("placa", placaSeleccionada)
                intent.putExtra("cobro", cobroSeleccionado)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona un visitante primero", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



