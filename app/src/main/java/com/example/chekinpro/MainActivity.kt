package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

      /*  // Recuperamos las preferencias para saber si hay una sesión iniciada
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val usuarioLogueado = prefs.getBoolean("usuarioLogueado", false)

        // Si no hay sesión iniciada, enviamos al Login
        if (!usuarioLogueado) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // Cerramos MainActivity para que no quede en el stack
            return // Importante: salimos para no seguir ejecutando más código
        }

        // Si hay sesión iniciada, cargamos la vista normal de MainActivity
        setContentView(R.layout.activity_main)

        // Ajustamos los márgenes para evitar que la barra de estado tape contenido
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botón para ir al Login
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // TextView para ir a Registrarse (SignUp)
        val signupTextView = findViewById<TextView>(R.id.textView2)
        signupTextView.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }*/
    }
}


