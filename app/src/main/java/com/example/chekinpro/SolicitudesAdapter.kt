package com.example.chekinpro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SolicitudesAdapter(
    private val lista: List<VisitanteEnCurso>,
    private val onClick: (VisitanteEnCurso) -> Unit
) : RecyclerView.Adapter<SolicitudesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.txtNombre)
        val placa = view.findViewById<TextView>(R.id.txtPlaca)
        val contenedor = view.findViewById<LinearLayout>(R.id.itemContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solicitud, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val visitante = lista[position]
        holder.nombre.text = visitante.nombre
        holder.placa.text = "Placa: ${visitante.placa}"
        holder.contenedor.setOnClickListener {
            onClick(visitante)
        }
    }

    override fun getItemCount(): Int = lista.size
}
