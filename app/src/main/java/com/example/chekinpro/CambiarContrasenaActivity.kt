package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth


class CambiarContrasenaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasena)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val flechaAtras = findViewById<ImageView>(R.id.flechaAtras)
        flechaAtras.setOnClickListener {
            finish()
        }

        val inputActual = findViewById<EditText>(R.id.inputActual)
        val inputNueva = findViewById<EditText>(R.id.inputNueva)
        val inputConfirmar = findViewById<EditText>(R.id.inputConfirmar)
        val btnCambiar = findViewById<Button>(R.id.btnCambiar)

        btnCambiar.setOnClickListener {
            val actual = inputActual.text.toString().trim()
            val nueva = inputNueva.text.toString().trim()
            val confirmar = inputConfirmar.text.toString().trim()

            if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nueva != confirmar) {
                Toast.makeText(this, "La nueva contraseña no coincide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = FirebaseAuth.getInstance().currentUser
            val email = user?.email

            if (user != null && email != null) {
                val credential =
                    com.google.firebase.auth.EmailAuthProvider.getCredential(email, actual)

                // Reautenticar al usuario
                user.reauthenticate(credential).addOnSuccessListener {
                    // Ahora sí cambiamos la contraseña
                    user.updatePassword(nueva).addOnSuccessListener {
                        Toast.makeText(this, "Contraseña actualizada con éxito", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, ConfirmacionAjustes::class.java))
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Error al actualizar la contraseña",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "La contraseña actual no es correcta", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Sesión no encontrada", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

