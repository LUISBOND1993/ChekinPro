package com.example.chekinpro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class VisitasFinalizadasAdapter(
    private val lista: List<VisitaFinalizada>
) : RecyclerView.Adapter<VisitasFinalizadasAdapter.VisitaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_visita_finalizada, parent, false)
        return VisitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: VisitaViewHolder, position: Int) {
        val visita = lista[position]

        holder.txtNombre.text = visita.nombre
        holder.txtPlaca.text = "Placa: ${visita.placa}"
        holder.txtTorreApto.text = "Torre ${visita.torre} - Apto ${visita.apto}"

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.txtFecha.text = "Ingreso: ${sdf.format(visita.fechaRegistro)}"
        holder.txtFechaSalida.text = "Salida: ${sdf.format(visita.fechaSalida)}"
        holder.txtConjunto.text = "Conjunto: ${visita.conjunto}"


        holder.txtDuracion.text = "Duración: ${visita.totalMinutos} min"
        holder.txtTotal.text = "Total: $${visita.totalPago} COP"

        holder.txtTipo.text = "Tipo: ${if (visita.tipo == "Rapido") "Registro Rápido" else "Pre-Registro"}"
    }

    override fun getItemCount(): Int = lista.size

    inner class VisitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtPlaca: TextView = view.findViewById(R.id.txtPlaca)
        val txtTorreApto: TextView = view.findViewById(R.id.txtTorreApto)
        val txtFecha: TextView = view.findViewById(R.id.txtFecha)
        val txtFechaSalida: TextView = view.findViewById(R.id.txtFechaSalida)
        val txtDuracion: TextView = view.findViewById(R.id.txtDuracion)
        val txtTotal: TextView = view.findViewById(R.id.txtTotal)
        val txtTipo: TextView = view.findViewById(R.id.txtTipo)
        val txtConjunto: TextView = itemView.findViewById(R.id.txtConjunto)

    }
}
