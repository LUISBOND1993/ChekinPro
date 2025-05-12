package com.example.chekinpro

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class HistorialDeVisitas : AppCompatActivity() {

    private lateinit var fechaInicialEditText: EditText
    private lateinit var fechaFinalEditText: EditText
    private var fechaInicial = ""
    private var fechaFinal = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_de_visitas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Flecha de regreso al menú
        val flechaRegresar = findViewById<ImageView>(R.id.flechaRegresar)
        flechaRegresar.setOnClickListener {
            startActivity(Intent(this, Menu::class.java))
            finish()
        }

        // Referencias
        fechaInicialEditText = findViewById(R.id.fechaInicialEditText)
        fechaFinalEditText = findViewById(R.id.fechaFinalEditText)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val btnFiltrar = findViewById<MaterialButton>(R.id.btnFiltrar)

        // Formato de fecha
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Picker para Fecha Inicial
        fechaInicialEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    fechaInicial = formato.format(calendar.time)
                    fechaInicialEditText.setText(fechaInicial)
                    calendarView.date = calendar.timeInMillis // Mostrar en el calendario
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Picker para Fecha Final
        fechaFinalEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    fechaFinal = formato.format(calendar.time)
                    fechaFinalEditText.setText(fechaFinal)
                    calendarView.date = calendar.timeInMillis // Mostrar en el calendario
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Acción del botón Filtrar
        btnFiltrar.setOnClickListener {
            if (fechaInicial.isEmpty() || fechaFinal.isEmpty()) {
                Toast.makeText(this, "Debes seleccionar ambas fechas", Toast.LENGTH_SHORT).show()
            } else {
                // Centrar calendario en la fecha inicial
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val fechaInicioDate = sdf.parse(fechaInicial)
                if (fechaInicioDate != null) {
                    calendarView.date = fechaInicioDate.time
                }

                // Mostrar rango como confirmación rápida
                Toast.makeText(this, "Consultando del $fechaInicial al $fechaFinal", Toast.LENGTH_SHORT).show()

                // Ir a ItemVisitaActivity
                val intent = Intent(this, ItemVisitaActivity::class.java)
                intent.putExtra("fechaInicio", fechaInicial)
                intent.putExtra("fechaFin", fechaFinal)
                startActivity(intent)
            }
        }

    }
}
