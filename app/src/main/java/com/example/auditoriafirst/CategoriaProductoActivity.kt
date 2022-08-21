package com.example.auditoriafirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.adapter.CategoriaProductoAdapter
import com.example.auditoriafirst.adapter.NegocioAdapter
import com.example.auditoriafirst.data.Entities.Negocio
import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.services.medicion.*
import com.example.auditoriafirst.services.negocio.NegocioCategoriaResponse
import com.example.auditoriafirst.services.negocio.NegocioInput
import com.example.auditoriafirst.services.negocio.NegocioService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria_producto)

        var codigo_categoria = intent.getStringExtra("cod_categoria")
        var cod_negocio = intent.getStringExtra("cod_negocio")
        var descripcion_categoria = intent.getStringExtra("descripcion_categoria")

        val categoriaProductoInput = CategoriaProductoInput(codigo_categoria.toString(),cod_negocio.toString())

        var service = CategoriaProductoService.create()
        var apiInterface = service.productos(categoriaProductoInput)

        apiInterface.enqueue( object : Callback<CategoriaProductoResponse>
        {
            override fun onResponse(
                call: Call<CategoriaProductoResponse>,
                response: Response<CategoriaProductoResponse>
            ) {
                var productos = response.body()

                if (productos != null) {
                    if(productos.error.equals("0")){

                        lifecycleScope.launch{
                            var arr_codigo : MutableList<String> = mutableListOf()
                            var arr_descripcion : MutableList<String> = mutableListOf()
                            var arr_producto : MutableList<Producto> = mutableListOf()


                            for(producto in productos.body){
                                arr_codigo.add(producto.sku)
                                arr_descripcion.add(producto.descripcion)
                                var n_producto= Producto(producto.id,producto.sku,producto.descripcion,"2")
                                arr_producto.add(n_producto)
                            }


                            var adapter = CategoriaProductoAdapter()
                            adapter.setList(arr_codigo,arr_descripcion,arr_producto)

                            adapter.onItemClick = { pro,codigo_producto,descripcion_producto ->
                                /* negocio_categoria_activity.putExtra("cod_negocio",codigo_negocio.text)
                                 negocio_categoria_activity.putExtra("descripcion_negocio",descripcion_negocio.text)
                                 startActivity(negocio_categoria_activity)*/

                            }

                            val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
                            var recyclerView = findViewById<RecyclerView>(R.id.recyclerCategoriaProductoView)
                            recyclerView.layoutManager = linearLayoutManager
                            recyclerView.adapter = adapter
                        }
                    }else if(productos.error.equals("1")){
                        //txtMensajeBusqueda.text = "Medición no encontrada"
                    }

                }


            }

            override fun onFailure(call: Call<CategoriaProductoResponse>, t: Throwable) {

                TODO(t.toString() + "CategoriaProductoResponse")
            }

        })

    }
}