package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        // Inicializar Firebase//
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Referencias UI
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val plateEditText = findViewById<EditText>(R.id.plateEditText)
        val conjuntoEditText = findViewById<EditText>(R.id.conjuntoEditText)
        val torreEditText = findViewById<EditText>(R.id.torreEditText)
        val aptoEditText = findViewById<EditText>(R.id.aptoEditText)

        val userTypeGroup = findViewById<RadioGroup>(R.id.userTypeGroup)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val loginTextView = findViewById<TextView>(R.id.signup_loginText)

        // Botón "¿Ya tienes cuenta?"
        loginTextView.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        // Botón de registro
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val plate = plateEditText.text.toString().trim()
            val conjunto = conjuntoEditText.text.toString().trim()
            val torre = torreEditText.text.toString().trim()
            val apto = aptoEditText.text.toString().trim()

            val userType = when (userTypeGroup.checkedRadioButtonId) {
                R.id.visitorRadio -> "Visitante"
                R.id.residentRadio -> "Residente"
                else -> ""
            }

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email y contraseña son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userType.isEmpty()) {
                Toast.makeText(this, "Debe seleccionar el tipo de usuario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Registro en Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val uid = result.user?.uid

                    // Guardar datos adicionales en Firestore
                    val userMap = mapOf(
                        "email" to email,
                        "telefono" to phone,
                        "placa" to plate,
                        "conjunto" to conjunto,
                        "torre" to torre,
                        "apartamento" to apto,
                        "tipoUsuario" to userType
                    )

                    firestore.collection("usuarios")
                        .document(uid ?: "")
                        .set(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_LONG).show()
                            Handler(mainLooper).postDelayed({
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                                finish()
                            }, 2000)

                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error guardando datos", Toast.LENGTH_SHORT).show()

                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}
