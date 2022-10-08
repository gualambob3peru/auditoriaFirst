package com.example.auditoriafirst.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.R
import com.example.auditoriafirst.data.Entities.Negocio

class NegocioAdapter:RecyclerView.Adapter<NegocioAdapter.ViewHolder>() {
    var codigos : MutableList<String> = mutableListOf()
    var descripcions : MutableList<String> = mutableListOf()
    var buttons : MutableList<String> = mutableListOf()
    var onItemClick: ((Negocio,TextView,TextView) -> Unit)? = null
    var onItemArchivarClick : ((Negocio) -> Unit)? = null
    var onEnviarClick: ((Negocio) -> Unit)? = null
    var negocios : List<Negocio> = emptyList()

    fun setList(miList1: MutableList<String>,miList2: MutableList<String>,miList4:List<Negocio>){
        this.codigos = miList1
        this.descripcions = miList2
       // this.buttons = miList3
        this.negocios = miList4
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_negocio_layout,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemCodigo.text = this.codigos[i]
        viewHolder.itemDescripcion.text  = this.descripcions[i]
        viewHolder.itemNegocio = this.negocios[i]

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemCodigo: TextView
        var itemDescripcion:TextView
        var itemNegocio : Negocio
        var itemButton : Button
        var itemButtonArchivar : Button

        init{
            itemCodigo = itemView.findViewById(R.id.tCodigoProducto)
            itemDescripcion = itemView.findViewById(R.id.tDescripcionProducto)
            itemNegocio = Negocio(0)
            itemButton = itemView.findViewById(R.id.btnEnviar)
            itemButtonArchivar = itemView.findViewById(R.id.btnArchivar)

            itemView.setOnClickListener {
                onItemClick?.invoke(negocios[adapterPosition],itemCodigo,itemDescripcion)
            }

            itemButtonArchivar.setOnClickListener {
                onItemArchivarClick?.invoke(negocios[adapterPosition])
            }

            itemButton.setOnClickListener{
                onEnviarClick?.invoke(negocios[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return codigos.size
    }
}