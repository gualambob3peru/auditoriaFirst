package com.example.auditoriafirst

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.adapter.CategoriaProductoAdapter
import com.example.auditoriafirst.components.ProductDialog
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.services.medicion.CategoriaProductoInput
import kotlinx.coroutines.launch


class CategoriaProductoActivity : AppCompatActivity() {

    companion object {
        var listSkus = Array(1) {Array(7) {""} }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria_producto)

        val db = AuditoriaDb(this)

        var codigo_categoria = intent.getStringExtra("cod_categoria")
        var cod_negocio = intent.getStringExtra("cod_negocio")
        var descripcion_categoria = intent.getStringExtra("descripcion_categoria")

        val btnAgregarProducto = findViewById<ImageView>(R.id.btnAgregarProducto)
        val btnAgregarProd = findViewById<ImageView>(R.id.btnAgregarProd)

        val btnGuardar = findViewById<ImageView>(R.id.btnGuardar)
        val categoriaProductoInput = CategoriaProductoInput(codigo_categoria.toString(),cod_negocio.toString())

        val agregar_producto_activity = Intent(this, AgregarProductoActivity::class.java)
        val agregar_prod_activity = Intent(this, AgregarProdActivity::class.java)
        val negocio_categoria_activity = Intent(this, NegocioCategoria::class.java)
        val btnAtras = findViewById<ImageView>(R.id.btnAtras)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerCategoriaProductoView)
        recyclerView.layoutManager = linearLayoutManager
        var adapter = CategoriaProductoAdapter()
        lifecycleScope.launch{
            var productos = db.ProductoDao().getAllProductos_categoria(cod_negocio.toString(),codigo_categoria.toString())

            if(productos !=null){
                var arr_codigo : MutableList<String> = mutableListOf()
                var arr_descripcion : MutableList<String> = mutableListOf()
                var arr_producto : MutableList<Producto> = mutableListOf()

                listSkus = Array(productos.size) {Array(7) {""} }

               var misProductos = db.ProductoDao().getAllProductosNego(cod_negocio.toString(),codigo_categoria.toString())

                for(ind in misProductos.indices){
                    listSkus[ind][0] = misProductos[ind].compra
                    listSkus[ind][1] = misProductos[ind].inventario
                    listSkus[ind][2] = misProductos[ind].precio
                    listSkus[ind][3] = misProductos[ind].ve
                    listSkus[ind][5] = misProductos[ind].descripcion
                    listSkus[ind][6] = misProductos[ind].vant
                }


                for(ind in productos.indices){
                    arr_codigo.add(productos[ind].sku)
                    arr_descripcion.add(productos[ind].descripcion)
                    var n_producto= Producto(
                        productos[ind].id.toInt(),
                        productos[ind].sku,
                        "cate",
                        "nego",
                        productos[ind].descripcion,
                        listSkus[ind][0] ,
                        listSkus[ind][1],
                        listSkus[ind][2] ,
                        listSkus[ind][3] ,
                        "",
                        "",
                        productos[ind].vant
                        )

                    arr_producto.add(n_producto)

                }

                for(ind in listSkus.indices){
                    listSkus[ind][4] = productos[ind].sku
                }


                adapter.setList(arr_codigo,arr_descripcion,arr_producto)

                adapter.onCompraKey = { position, variable, itemCompra ->
                    listSkus[position][variable] = itemCompra.text.toString()
                }
                adapter.onInventarioKey = { position, variable, itemInventario ->
                    listSkus[position][variable] = itemInventario.text.toString()
                }
                adapter.onPrecioKey = { position, variable, itemPrecio ->
                    listSkus[position][variable] = itemPrecio.text.toString()
                }
                adapter.onVeKey = { position, variable, itemVe ->
                    listSkus[position][variable] = itemVe.text.toString()
                }
                /*
                adapter.onItemClick = { position,tCompra,tInventario,tPrecio,tVant ->
                    var dialog = ProductDialog(
                        listSkus[position][4],
                        cod_negocio.toString(),
                        codigo_categoria.toString(),
                        listSkus[position][0],
                        listSkus[position][1],
                        listSkus[position][2],
                        listSkus[position][3])
                    dialog.show(supportFragmentManager,"customDialog")
                    dialog.onItemClick = {compra,inventario,precio,vant->

                        lifecycleScope.launch {
                            db.ProductoDao().update_sku(
                                listSkus[position][4],
                                cod_negocio.toString(),
                                codigo_categoria.toString(),
                                compra,
                                inventario,
                                precio,
                                vant
                            )

                            listSkus[position][0] = compra
                            listSkus[position][1] = inventario
                            listSkus[position][2] = precio
                            listSkus[position][3] = vant

                        }

                        tCompra.setText(compra)
                        tInventario.setText(inventario)
                        tPrecio.setText(precio)
                        tVant.setText(vant)
                        dialog.dismiss()
                    }
                }
                */


                recyclerView.adapter = adapter

            }else{
                //No tiene ninguna categoria
            }
        }


        btnAtras.setOnClickListener {
            negocio_categoria_activity.putExtra("cod_negocio",cod_negocio.toString())
            negocio_categoria_activity.putExtra("descripcion_negocio","")
            startActivity(negocio_categoria_activity)
        }

        btnAgregarProducto.setOnClickListener {
            agregar_producto_activity.putExtra("cod_categoria",codigo_categoria.toString())
            agregar_producto_activity.putExtra("cod_negocio",cod_negocio.toString())
            agregar_producto_activity.putExtra("desc_categoria",descripcion_categoria)
            startActivity(agregar_producto_activity)
        }

        btnAgregarProd.setOnClickListener {
            agregar_prod_activity.putExtra("cod_categoria",codigo_categoria.toString())
            agregar_prod_activity.putExtra("cod_negocio",cod_negocio.toString())
            agregar_prod_activity.putExtra("desc_categoria",descripcion_categoria)
            startActivity(agregar_prod_activity)
        }

        btnGuardar.setOnClickListener{
           lifecycleScope.launch {
               //db.ProductoDao().borrarTodo(intent.getStringExtra("cod_negocio").toString(),intent.getStringExtra("cod_categoria").toString())
               for(skus in listSkus){
                   db.ProductoDao().update_sku(
                       skus[4].toString(),
                       intent.getStringExtra("cod_negocio").toString(),
                       intent.getStringExtra("cod_categoria").toString(),
                       skus[0].toString(),
                       skus[1].toString(),
                       skus[2].toString(),
                       skus[3].toString()
                   );
               }
               Toast.makeText(applicationContext,"Productos Guardados", Toast.LENGTH_SHORT).show()
            }


        }
    }
}