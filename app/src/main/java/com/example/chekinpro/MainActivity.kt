package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Validamos si existe sesión iniciada
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val usuarioLogueado = prefs.getBoolean("usuarioLogueado", false)

        if (!usuarioLogueado) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_main)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Botón "Login"
            val button = findViewById<Button>(R.id.button)
            button.setOnClickListener {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }

            // TextView "Registrate"
            val signupTextView = findViewById<TextView>(R.id.textView2)
            signupTextView.setOnClickListener {
                val intent = Intent(this, SignUp::class.java)
                startActivity(intent)
            }
        }
    }
}

