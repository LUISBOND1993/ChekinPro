package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class RegistroDeVisitantes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_de_visitantes)

        // Ajuste de barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Firestore
        val db = FirebaseFirestore.getInstance()

        // Flecha para regresar al menú
        val flechaAtras = findViewById<ImageView>(R.id.flechaAtras)
        flechaAtras.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }

        // Referencias UI
        val campoNombre = findViewById<EditText>(R.id.inputNombre)
        val campoCC = findViewById<EditText>(R.id.inputCC)
        val campoTorre = findViewById<EditText>(R.id.inputTorre)
        val campoApto = findViewById<EditText>(R.id.inputApto)
        val campoConjunto = findViewById<EditText>(R.id.inputConjunto)
        val checkAutorizacion = findViewById<CheckBox>(R.id.checkAutorizacion)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        // Acción del botón registrar
        btnRegistrar.setOnClickListener {
            val nombre = campoNombre.text.toString().trim()
            val cc = campoCC.text.toString().trim()
            val torre = campoTorre.text.toString().trim()
            val apto = campoApto.text.toString().trim()
            val conjunto = campoConjunto.text.toString().trim()
            val autorizado = checkAutorizacion.isChecked

            if (nombre.isEmpty() || cc.isEmpty() || torre.isEmpty() || apto.isEmpty() || conjunto.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!autorizado) {
                Toast.makeText(this, "Debes autorizar el tratamiento de datos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val visitanteData = hashMapOf(
                "nombre" to nombre,
                "cedula" to cc,
                "torre" to torre,
                "apartamento" to apto,
                "conjunto" to conjunto,
                "tipoUsuario" to "Visitante",
                "fechaRegistro" to System.currentTimeMillis()
            )

            db.collection("visitante_rapido")
                .add(visitanteData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Visitante registrado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ConfirmacionRegistroActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}
