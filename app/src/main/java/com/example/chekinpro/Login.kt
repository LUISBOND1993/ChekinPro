package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // 🔐 Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val buttonLogin = findViewById<Button>(R.id.loginButton2)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)

        // 🔁 Ir a recuperación de contraseña
        forgotPassword.setOnClickListener {
            val intent = Intent(this, Olvidaste_contrasenia::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔐 Intentar iniciar sesión con FirebaseAuth
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Guardar sesión en SharedPreferences
                        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
                        prefs.edit {
                            putBoolean("usuarioLogueado", true)
                        }

                        // Ir al menú principal
                        val intent = Intent(this, Menu::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        Log.e("AUTH", "❌ Login fallido: \${task.exception?.message}")
                    }
                }
        }
    }
}
