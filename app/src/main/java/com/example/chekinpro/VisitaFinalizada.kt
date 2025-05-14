package com.example.chekinpro

import java.util.*

data class VisitaFinalizada(
    val id: String = "",
    val nombre: String = "",
    val placa: String = "",
    val torre: String = "",
    val apto: String = "",
    val fechaRegistro: Date = Date(),
    val fechaSalida: Date = Date(),
    val tipo: String = "",
    val totalMinutos: Long = 0,
    val totalPago: Long = 0,
    val conjunto: String = ""
)