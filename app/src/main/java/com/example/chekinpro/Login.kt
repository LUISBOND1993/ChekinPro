package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.loginButton2)
        buttonLogin.setOnClickListener {
            // Guardar que el usuario inició sesión
            val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("usuarioLogueado", true)
            editor.apply()


            // Ir al menú principal
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}
