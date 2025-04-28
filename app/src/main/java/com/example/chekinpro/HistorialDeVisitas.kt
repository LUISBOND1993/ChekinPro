package com.example.chekinpro
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HistorialDeVisitas : AppCompatActivity() {

    private var fechaSeleccionada: String = ""  // Variable para guardar la fecha seleccionada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_de_visitas)

        // Ajuste de barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botón de regreso al menú
        val flechaRegresar = findViewById<ImageView>(R.id.flechaRegresar)
        flechaRegresar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }

        // Captura la fecha seleccionada del calendario
        val calendario = findViewById<CalendarView>(R.id.calendarView)
        calendario.setOnDateChangeListener { _, year, month, dayOfMonth ->
            fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
        }

        // Acción del botón Filtrar
        val btnFiltrar = findViewById<MaterialButton>(R.id.btnFiltrar)
        btnFiltrar.setOnClickListener {
            if (fechaSeleccionada.isEmpty()) {
                Toast.makeText(this, "Selecciona una fecha en el calendario", Toast.LENGTH_SHORT).show()
            } else {
                // Navega a la pantalla item_visita
                val intent = Intent(this, ItemVisitaActivity::class.java)
                intent.putExtra("fecha", fechaSeleccionada) // (opcional) por si luego quieres usarla
                startActivity(intent)
            }
        }
    }
}



