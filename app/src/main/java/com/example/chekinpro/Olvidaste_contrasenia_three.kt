package com.example.chekinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Olvidaste_contrasenia_three : AppCompatActivity() {

    private lateinit var editNuevaContrasena: EditText
    private lateinit var editConfirmarContrasena: EditText
    private lateinit var btnCambiarContrasena: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olvidaste_contrasenia_three)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        editNuevaContrasena = findViewById(R.id.editTextNombreOlv)
        editConfirmarContrasena = findViewById(R.id.editTextNombre)
        btnCambiarContrasena = findViewById(R.id.btnRegistrar)

        email = intent.getStringExtra("email") ?: ""

        btnCambiarContrasena.setOnClickListener {
            val nueva = editNuevaContrasena.text.toString().trim()
            val confirmar = editConfirmarContrasena.text.toString().trim()

            if (nueva.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Completa ambos campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nueva != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.collection("usuarios")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { docs ->
                    if (docs.isEmpty) {
                        Toast.makeText(this, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }

                    val docId = docs.documents[0].id

                    db.collection("usuarios").document(docId)
                        .update("password", nueva)
                        .addOnSuccessListener {
                            auth.fetchSignInMethodsForEmail(email)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val methods = task.result?.signInMethods
                                        if (methods.isNullOrEmpty()) {
                                            Toast.makeText(this, "Cuenta no registrada en Auth", Toast.LENGTH_SHORT).show()
                                        } else {
                                            auth.sendPasswordResetEmail(email)
                                                .addOnSuccessListener {
                                                    Toast.makeText(this, "Contraseña actualizada. Revisa tu correo para confirmar", Toast.LENGTH_LONG).show()
                                                    startActivity(Intent(this, Login::class.java))
                                                    finish()
                                                }
                                                .addOnFailureListener {
                                                    Toast.makeText(this, "Error al enviar correo de confirmación", Toast.LENGTH_LONG).show()
                                                }
                                        }
                                    } else {
                                        Toast.makeText(this, "Error al verificar usuario en Auth", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al actualizar contraseña en Firestore", Toast.LENGTH_LONG).show()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error de conexión con Firestore", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
