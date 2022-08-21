package com.example.auditoriafirst.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.R
import com.example.auditoriafirst.data.Entities.Producto

class CategoriaProductoAdapter:RecyclerView.Adapter<CategoriaProductoAdapter.ViewHolder>() {
    var codigos : MutableList<String> = mutableListOf()
    var descripcions : MutableList<String> = mutableListOf()
    var buttons : MutableList<String> = mutableListOf()
    var onItemClick: ((Producto,TextView,TextView) -> Unit)? = null
    var productos : List<Producto> = emptyList()

    fun setList(miList1: MutableList<String>,miList2: MutableList<String>,miList4:List<Producto>){
        this.codigos = miList1
        this.descripcions = miList2
       // this.buttons = miList3
        this.productos = miList4
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_categoria_producto_layout,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemCodigo.text = this.codigos[i]
        viewHolder.itemDescripcion.text  = this.descripcions[i]
        viewHolder.itemProducto = this.productos[i]

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemCodigo: TextView
        var itemDescripcion:TextView
        var itemProducto : Producto
        var itemButton : Button

        init{
            itemCodigo = itemView.findViewById(R.id.tCodigoProducto)
            itemDescripcion = itemView.findViewById(R.id.tDescripcionProducto)
            itemProducto = Producto()
            itemButton = itemView.findViewById(R.id.btnVerCate)

            itemButton.setOnClickListener {
                onItemClick?.invoke(productos[adapterPosition],itemCodigo,itemDescripcion)
            }
        }
    }

    override fun getItemCount(): Int {
        return codigos.size
    }
}