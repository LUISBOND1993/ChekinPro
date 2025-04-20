package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ItemVisitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_visita)

        val retroceso = findViewById<ImageView>(R.id.retroceso)
        retroceso.setOnClickListener {
            val intent = Intent(this, HistorialDeVisitas::class.java)
            startActivity(intent)
            finish()
        }
    }
}
