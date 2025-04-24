package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.*


class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        // Referencias a los campos
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val plateEditText = findViewById<EditText>(R.id.plateEditText)
        val conjuntoEditText = findViewById<EditText>(R.id.conjuntoEditText)
        val torreEditText = findViewById<EditText>(R.id.torreEditText)
        val aptoEditText = findViewById<EditText>(R.id.aptoEditText)

        // RadioButtons
        val userTypeGroup = findViewById<RadioGroup>(R.id.userTypeGroup)
        val visitorRadio = findViewById<RadioButton>(R.id.visitorRadio)
        val residentRadio = findViewById<RadioButton>(R.id.residentRadio)

        // Botón
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        // Acción del botón
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

            // Validaciones básicas
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email y contraseña son obligatorios", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (userType.isEmpty()) {
                Toast.makeText(this, "Debe seleccionar el tipo de usuario", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Mostrar resumen de datos capturados (modo demo)
            val resumen = """
                Email: $email
                Contraseña: $password
                Teléfono: $phone
                Placa: $plate
                Conjunto: $conjunto
                Torre: $torre
                Apto: $apto
                Tipo de usuario: $userType
            """.trimIndent()

            Toast.makeText(this, resumen, Toast.LENGTH_LONG).show()


            val buttonLogin = findViewById<Button>(R.id.signUpButton)
            buttonLogin.setOnClickListener {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                // Aquí puedes enviar estos datos a una API, guardar en Firebase, etc.
            }

            val loginTextView = findViewById<TextView>(R.id.signup_loginText)
            loginTextView.setOnClickListener {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }

        }
    }
}
