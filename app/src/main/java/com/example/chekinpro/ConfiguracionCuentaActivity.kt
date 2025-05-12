package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ConfiguracionCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion_cuenta)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val flechaAtras = findViewById<ImageView>(R.id.flechaAtras)
        flechaAtras.setOnClickListener {
            startActivity(Intent(this, Menu::class.java))
            finish()
        }

        val btnCambiarContrasena = findViewById<Button>(R.id.btnCambiarContrasena)
        btnCambiarContrasena.setOnClickListener {
            startActivity(Intent(this, CambiarContrasenaActivity::class.java))
        }

        val inputEmail = findViewById<EditText>(R.id.inputEmail)
        val inputTelefono = findViewById<EditText>(R.id.inputTelefono)
        val inputPlaca = findViewById<EditText>(R.id.inputPlaca)
        val inputTorre = findViewById<EditText>(R.id.inputTorre)
        val inputApto = findViewById<EditText>(R.id.inputApto)
        inputEmail.isEnabled = false  // Bloqueamos edición del email

        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        // Cargar datos actuales del usuario
        if (user != null) {
            db.collection("usuarios").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        inputEmail.setText(user.email)
                        inputTelefono.setText(document.getString("telefono") ?: "")
                        inputPlaca.setText(document.getString("placa") ?: "")
                        inputTorre.setText(document.getString("torre") ?: "")
                        inputApto.setText(document.getString("apto") ?: "")
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                }
        }

        val btnGuardarCambios = findViewById<Button>(R.id.btnGuardarCambios)
        btnGuardarCambios.setOnClickListener {
            val telefono = inputTelefono.text.toString().trim()
            val placa = inputPlaca.text.toString().trim()
            val torre = inputTorre.text.toString().trim()
            val apto = inputApto.text.toString().trim()

            if (telefono.isEmpty() || placa.isEmpty() || torre.isEmpty() || apto.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (user == null) {
                Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val datosActualizados = mapOf(
                "telefono" to telefono,
                "placa" to placa,
                "torre" to torre,
                "apto" to apto
            )

            db.collection("usuarios").document(user.uid)
                .update(datosActualizados)
                .addOnSuccessListener {
                    Toast.makeText(this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ConfirmacionAjustes::class.java))
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(this, Menu::class.java))
                        finish()
                    }, 5000)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar cambios", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
