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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.CategoriaProductoActivity
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
    var onItemClick: ((Int,TextView,TextView,TextView,TextView) -> Unit)? = null
    var productos : List<Producto> = emptyList()
    var listText : List<TextView> = mutableListOf()
    
    fun setList(miList1: MutableList<String>,miList2: MutableList<String>,miList4:List<Producto>){
        this.codigos = miList1
        this.descripcions = miList2
       // this.buttons = miList3
        this.productos = miList4


    }

    fun getList(viewHolder: ViewHolder):TextView{
        return viewHolder.itemCompra
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_categoria_producto_layout,viewGroup,false)

        /*  val isa = viewGroup.childCount
        var itemCodigo =  v.findViewById<TextView>(R.id.tCodigoProducto)
        itemCodigo.text = this.codigos[isa]

        var itemDescripcion =  v.findViewById<TextView>(R.id.tDescripcionProducto)
        itemDescripcion.text = this.descripcions[isa]




        val mCompra = v.findViewById<TextView>(R.id.inputCompra)
        if(productos[isa].compra==""){
            productos[isa].compra="0"
        }
        if(productos[isa].inventario==""){
            productos[isa].inventario="0"
        }
        if(productos[isa].precio==""){
            productos[isa].precio="0"
        }

        if(productos[isa].ve==""){
            productos[isa].ve="0"
        }

        mCompra.setText(productos[isa].compra)
        val mInventario = v.findViewById<TextView>(R.id.inputInventario)
        mInventario.setText(productos[isa].inventario)
        val mPrecio = v.findViewById<TextView>(R.id.inputPrecio)
        mPrecio.setText(productos[isa].precio)
        val mve = v.findViewById<TextView>(R.id.inputVe)
        mve.setText(productos[isa].ve)

        val mVant = v.findViewById<TextView>(R.id.txtVant)
        mVant.setText(productos[isa].vant)*/

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemProducto = this.productos[i]
        viewHolder.itemCodigo.text = this.codigos[i]
        viewHolder.itemDescripcion.text  = this.descripcions[i]
        viewHolder.itemProducto = this.productos[i]


        viewHolder.itemCompra.setText(CategoriaProductoActivity.listSkus[i][0])
        viewHolder.itemInventario.setText(CategoriaProductoActivity.listSkus[i][1])
        viewHolder.itemPrecio.setText(CategoriaProductoActivity.listSkus[i][2])
        viewHolder.itemVe.setText(CategoriaProductoActivity.listSkus[i][3])

     /*   viewHolder.itemCompra.setText(productos[i].compra)
        viewHolder.itemInventario.setText(productos[i].inventario)
        viewHolder.itemPrecio.setText(productos[i].precio)
        viewHolder.itemVe.setText(productos[i].ve)*/


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


            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition,itemCompra,itemInventario,itemPrecio,itemVe)
            }
            itemCompra.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    onCompraKey?.invoke(adapterPosition,0,itemView.findViewById(R.id.inputCompra))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {

                }
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {

                }
            })


            itemInventario.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    onInventarioKey?.invoke(adapterPosition,1,itemInventario)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {}
            })


            itemPrecio.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    onPrecioKey?.invoke(adapterPosition,2,itemPrecio)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {}
            })


            itemVe.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    onVeKey?.invoke(adapterPosition,3,itemVe)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {}
            })


        }

    }

    override fun getItemCount(): Int {
        return codigos.size
    }


}