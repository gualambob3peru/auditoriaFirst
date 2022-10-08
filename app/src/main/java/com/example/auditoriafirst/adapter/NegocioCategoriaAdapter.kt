package com.example.auditoriafirst.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.R
import com.example.auditoriafirst.data.Entities.Categoria

class NegocioCategoriaAdapter:RecyclerView.Adapter<NegocioCategoriaAdapter.ViewHolder>() {
    var codigos : MutableList<String> = mutableListOf()
    var descripcions : MutableList<String> = mutableListOf()
    var buttons : MutableList<String> = mutableListOf()
    var onItemClick: ((Categoria,TextView,TextView) -> Unit)? = null
    var categorias : List<Categoria> = emptyList()

    fun setList(miList1: MutableList<String>,miList2: MutableList<String>,miList4:List<Categoria>){
        this.codigos = miList1
        this.descripcions = miList2
       // this.buttons = miList3
        this.categorias = miList4
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_negocio_categoria_layout,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemCodigo.text = this.codigos[i]
        viewHolder.itemDescripcion.text  = this.descripcions[i]
        viewHolder.itemCategoria = this.categorias[i]

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemCodigo: TextView
        var itemDescripcion:TextView
        var itemCategoria : Categoria
        //var itemButton : Button

        init{
            itemCodigo = itemView.findViewById(R.id.tCodigoProducto)
            itemDescripcion = itemView.findViewById(R.id.tDescripcionProducto)
            itemCategoria = Categoria()
            //itemButton = itemView.findViewById(R.id.btnVerCate)

            itemView.setOnClickListener {
                onItemClick?.invoke(categorias[adapterPosition],itemCodigo,itemDescripcion)
            }
        }
    }

    override fun getItemCount(): Int {
        return codigos.size
    }
}