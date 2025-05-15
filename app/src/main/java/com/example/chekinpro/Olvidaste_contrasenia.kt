package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class Olvidaste_contrasenia : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var btnEnviarCodigo: Button
    private var generatedCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olvidaste_contrasenia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.editTextNombre)
        btnEnviarCodigo = findViewById(R.id.btnRegistrar)

        btnEnviarCodigo.setOnClickListener {
            val email = editTextEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa tu email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val methods = task.result?.signInMethods
                    if (methods.isNullOrEmpty()) {
                        Toast.makeText(this, "No existe una cuenta con ese correo", Toast.LENGTH_SHORT).show()
                    } else {
                        // Generar código OTP
                        generatedCode = generarCodigoOTP()
                        Log.d("OTP", "Código generado: $generatedCode")

                        // Simulación de envío (en producción, usar API de email)
                        Toast.makeText(this, "Código enviado a tu correo (simulado)", Toast.LENGTH_LONG).show()

                        // Ir a ValidarCodigoActivity
                        val intent = Intent(this, Olvidaste_contrasenia_two::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("codigo", generatedCode)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun generarCodigoOTP(): String {
        val random = Random()
        return (100000 + random.nextInt(900000)).toString()
    }
}
