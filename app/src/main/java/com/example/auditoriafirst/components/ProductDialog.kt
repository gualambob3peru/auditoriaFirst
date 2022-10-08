package com.example.auditoriafirst.components

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContentInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.R
import com.example.auditoriafirst.data.Database.AuditoriaDb

import com.example.auditoriafirst.databinding.ActivityDialogProductoBinding
import kotlinx.coroutines.launch

class ProductDialog(
    var sku : String,
    var cod_negocio : String,
    var cod_categoria : String,
    var tCompra : String,
    var tInventario : String,
    var tPrecio : String,
    var tVant : String
):DialogFragment() {
    var onItemClick: ((String,String,String,String) -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = AuditoriaDb(requireContext())

        var rootView : View = inflater.inflate(R.layout.modal_producto, container,false)

        var compra = rootView.findViewById<EditText>(R.id.modalTCompra)
        compra.setText(tCompra.toString())

        var inventario = rootView.findViewById<EditText>(R.id.modalTInventario)
        inventario.setText(tInventario.toString())

        var precio = rootView.findViewById<EditText>(R.id.modalTPrecio)
        precio.setText(tPrecio.toString())

        var vant = rootView.findViewById<EditText>(R.id.modalTVant)
        vant.setText(tVant.toString())

        var btnGuardar = rootView.findViewById<Button>(R.id.btnGuardarModalProducto)

        btnGuardar.setOnClickListener {
            onItemClick?.invoke( compra.text.toString(),inventario.text.toString(),precio.text.toString(),vant.text.toString())
        }

        /*btnGuardar.setOnClickListener {
            lifecycleScope.launch {
                db.ProductoDao().update_sku(
                    sku,
                    cod_negocio,
                    cod_categoria,
                    compra.text.toString(),
                    inventario.text.toString(),
                    precio.text.toString(),
                    vant.text.toString()
                )

                dismiss()
            }
        }*/




        return rootView
    }
    /*inner class View(itemView: View) : DialogFragment(view){
        var compra = itemView.findViewById<EditText>(R.id.modalTCompra)
        var inventario = itemView.findViewById<EditText>(R.id.modalTInventario)
        var precio = itemView.findViewById<EditText>(R.id.modalTPrecio)
        var vant = itemView.findViewById<EditText>(R.id.modalTVant)
        var btnGuardar = itemView.findViewById<Button>(R.id.btnGuardarModalProducto)

        init {
            btnGuardar.setOnClickListener {
                onItemClick?.invoke( compra.text.toString(),inventario.text.toString(),precio.text.toString(),vant.text.toString())
            }

        }
    }*/



}