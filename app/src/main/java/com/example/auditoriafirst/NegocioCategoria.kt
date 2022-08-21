package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.adapter.NegocioAdapter
import com.example.auditoriafirst.adapter.NegocioCategoriaAdapter
import com.example.auditoriafirst.data.Entities.Categoria
import com.example.auditoriafirst.data.Entities.Negocio
import com.example.auditoriafirst.services.negocio.NegocioCategoriaResponse
import com.example.auditoriafirst.services.negocio.NegocioInput
import com.example.auditoriafirst.services.negocio.NegocioResponse
import com.example.auditoriafirst.services.negocio.NegocioService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NegocioCategoria : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_negocio_categoria)

        var codigo_negocio = intent.getStringExtra("cod_negocio")
        var descripcion_negocio = intent.getStringExtra("descripcion_negocio")

        val negocioInput = NegocioInput(codigo_negocio.toString())
        val categoria_producto_activity = Intent(this, CategoriaProductoActivity::class.java)

        var service = NegocioService.create()
        var apiInterface = service.categorias(negocioInput)

        apiInterface.enqueue( object : Callback<NegocioCategoriaResponse>
        {
            override fun onResponse(
                call: Call<NegocioCategoriaResponse>,
                response: Response<NegocioCategoriaResponse>
            ) {
                var categorias = response.body()

                if (categorias != null) {
                    if(categorias.error.equals("0")){

                        lifecycleScope.launch{
                            var arr_codigo : MutableList<String> = mutableListOf()
                            var arr_descripcion : MutableList<String> = mutableListOf()
                            var arr_categoria : MutableList<Categoria> = mutableListOf()


                            for(categoria in categorias.body){
                                arr_codigo.add(categoria.codigo)
                                arr_descripcion.add(categoria.direccion)
                                var n_categoria= Categoria(categoria.id,categoria.codigo,categoria.direccion,"2")
                                arr_categoria.add(n_categoria)
                            }


                            var adapter = NegocioCategoriaAdapter()
                            adapter.setList(arr_codigo,arr_descripcion,arr_categoria)

                            adapter.onItemClick = { pro,codigo_categoria,descripcion_categoria ->
                                categoria_producto_activity.putExtra("cod_negocio",codigo_negocio)
                                categoria_producto_activity.putExtra("cod_categoria",codigo_categoria.text)
                                startActivity(categoria_producto_activity)

                            }

                            val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
                            var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNegocioCategoria)
                            recyclerView.layoutManager = linearLayoutManager
                            recyclerView.adapter = adapter
                        }
                    }else if(categorias.error.equals("1")){
                        //txtMensajeBusqueda.text = "Medición no encontrada"
                    }

                }


            }

            override fun onFailure(call: Call<NegocioCategoriaResponse>, t: Throwable) {

                TODO(t.toString() + "NegocioCategoriaResponse")
            }

        })
    }
}