package com.example.auditoriafirst.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.R
import com.example.auditoriafirst.data.Entities.Producto
import org.w3c.dom.Text

class CategoriaProductoAdapter:RecyclerView.Adapter<CategoriaProductoAdapter.ViewHolder>() {
    var codigos : MutableList<String> = mutableListOf()
    var descripcions : MutableList<String> = mutableListOf()
    var buttons : MutableList<String> = mutableListOf()
    //var onItemClick: ((Producto,TextView,TextView) -> Unit)? = null
    //position, variable, cant
    var onCompraKey: ((Int,Int,EditText) -> Unit)? = null
    var onInventarioKey: ((Int,Int,EditText) -> Unit)? = null
    var onPrecioKey: ((Int,Int,EditText) -> Unit)? = null
    var onVeKey: ((Int,Int,EditText) -> Unit)? = null
    var productos : List<Producto> = emptyList()
    var listText : List<EditText> = mutableListOf()
    
    fun setList(miList1: MutableList<String>,miList2: MutableList<String>,miList4:List<Producto>){
        this.codigos = miList1
        this.descripcions = miList2
       // this.buttons = miList3
        this.productos = miList4
    }

    fun getList(viewHolder: ViewHolder):EditText{
        return viewHolder.itemCompra
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_categoria_producto_layout,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemCodigo.text = this.codigos[i]
        viewHolder.itemDescripcion.text  = this.descripcions[i]
        viewHolder.itemProducto = this.productos[i]



            viewHolder.itemCompra.setText(productos[i].compra)
            viewHolder.itemInventario.setText(productos[i].inventario)
            viewHolder.itemPrecio.setText(productos[i].precio)
            viewHolder.itemVe.setText(productos[i].ve)
        viewHolder.itemVant.text = productos[i].vant

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemCodigo: TextView
        var itemDescripcion:TextView
        var itemProducto : Producto
        var itemCompra : EditText
        var itemInventario : EditText
        var itemPrecio : EditText
        var itemVe : EditText
        var itemVant : TextView
      //  var itemButton : Button

        init{
            itemCodigo = itemView.findViewById(R.id.tCodigoProducto)
            itemDescripcion = itemView.findViewById(R.id.tDescripcionProducto)
            itemProducto = Producto()
            itemCompra = itemView.findViewById(R.id.inputCompra)
            itemVant = itemView.findViewById(R.id.txtVant)

            itemInventario = itemView.findViewById(R.id.inputInventario)
            itemPrecio = itemView.findViewById(R.id.inputPrecio)
            itemVe = itemView.findViewById(R.id.inputVe)

          /*  itemButton.setOnClickListener {
                onItemClick?.invoke(productos[adapterPosition],itemCodigo,itemDescripcion)
            }*/
            itemCompra.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    onCompraKey?.invoke(adapterPosition,0,itemCompra)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {}
            })


           /*     .setOnEditorActionListener{ v, keyCode, event ->
                onCompraKey?.invoke(adapterPosition,0,itemCompra)
                    true
            }*/
            itemInventario.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    onInventarioKey?.invoke(adapterPosition,1,itemInventario)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {}
            })

             /*   .setOnEditorActionListener { v, keyCode, event ->
                onInventarioKey?.invoke(adapterPosition,1,itemInventario)
                true
            }*/
            itemPrecio.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    onPrecioKey?.invoke(adapterPosition,2,itemPrecio)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {}
            })

               /* .setOnEditorActionListener { v, i, event ->
                onPrecioKey?.invoke(adapterPosition,2,itemPrecio)
                true
            }*/
            itemVe.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    onVeKey?.invoke(adapterPosition,3,itemVe)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {}
            })

              /*  .setOnEditorActionListener{ v, keyCode, event ->
                onVeKey?.invoke(adapterPosition,3,itemVe)
                true
            }*/

        }

    }

    override fun getItemCount(): Int {
        return codigos.size
    }


}