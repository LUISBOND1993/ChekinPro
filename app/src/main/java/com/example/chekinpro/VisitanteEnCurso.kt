package com.example.chekinpro

data class VisitanteEnCurso(
    val id: String = "",
    val nombre: String = "",
    val placa: String = "",
    val tipo: String = "", // "PreRegistro" o "Rapido"
    val fechaRegistro: com.google.firebase.Timestamp? = null
)
