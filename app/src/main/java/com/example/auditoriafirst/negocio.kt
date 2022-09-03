package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.adapter.NegocioAdapter
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.data.Entities.Negocio
import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.services.medicion.*
import com.example.auditoriafirst.services.negocio.NegocioInput
import com.example.auditoriafirst.services.negocio.NegocioResponse
import com.example.auditoriafirst.services.negocio.NegocioService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.auditoriafirst.shared.UsuarioApplication.Companion.prefs

class negocio : AppCompatActivity() {
    var usuario = prefs.getUsuario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_negocio)

        val db = AuditoriaDb(this)


        var medicion = intent.getStringExtra("medicion")
        val txtNumMedicion = findViewById<TextView>(R.id.txtNumMedicion)
        txtNumMedicion.text = medicion

        val negocio_detalle_activity = Intent(this, NegocioDetalle::class.java)
        val negocio_categoria_activity = Intent(this, NegocioCategoria::class.java)
        val agregar_negocio_activity = Intent(this, AgregarNegocioActivity::class.java)

        val btnBuscarNegocio = findViewById<Button>(R.id.btnBuscarNegocio)
        val inputBuscarNegocio = findViewById<EditText>(R.id.inputBuscarNegocio)
        val tMensajeBusqueda = findViewById<TextView>(R.id.tMensajeBusqueda)
        val btnAgregarNegocio = findViewById<Button>(R.id.btnAgregarNegocio)

        val medicionInput = CategoriaInput(medicion.toString())

        lifecycleScope.launch{
            var negocios = db.NegocioDao().getAll()

            if(negocios !=null){
                var arr_codigo : MutableList<String> = mutableListOf()
                var arr_descripcion : MutableList<String> = mutableListOf()
                var arr_negocio : MutableList<Negocio> = mutableListOf()

                for(negocio in negocios){
                    arr_codigo.add(negocio.codigo_negocio)
                    arr_descripcion.add(negocio.descripcion) // -->> Direccion
                    var n_negocio= Negocio(0,negocio.codigo_negocio,negocio.descripcion)
                    arr_negocio.add(n_negocio)
                }

                var adapter = NegocioAdapter()
                adapter.setList(arr_codigo,arr_descripcion,arr_negocio)

                adapter.onItemClick = { pro,codigo_negocio,descripcion_negocio ->
                    negocio_categoria_activity.putExtra("cod_negocio",codigo_negocio.text)
                    negocio_categoria_activity.putExtra("descripcion_negocio",descripcion_negocio.text)
                    startActivity(negocio_categoria_activity)
                }
                adapter.onEnviarClick = {miNegocio ->
                    lifecycleScope.launch{
                        var productos = db.ProductoDao().getAllProductos_negocio(miNegocio.codigo_negocio)
                        subirProductos(productos)
                    }

                }

                val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
                var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNegocio)
                recyclerView.layoutManager = linearLayoutManager
                recyclerView.adapter = adapter

            }else{
                //No tiene ningun negocio
            }
        }




        btnBuscarNegocio.setOnClickListener {
            if(inputBuscarNegocio.text.toString().equals("") ){
                //tmensaje.text = "Debe rellenar todos los campos"
            }else{
                btnBuscarNegocio.isEnabled= false

                val negocioInput = NegocioInput(inputBuscarNegocio.text.toString())

                var service = NegocioService.create()
                var apiInterface = service.negocio(negocioInput)

                apiInterface.enqueue( object : Callback<NegocioResponse>
                {
                    override fun onResponse(
                        call: Call<NegocioResponse>,
                        response: Response<NegocioResponse>
                    ) {
                        var negocio = response.body()

                        if (negocio != null) {
                            if(negocio.error.equals("0")){

                                lifecycleScope.launch{
                                    negocio_detalle_activity.putExtra("cod_negocio",inputBuscarNegocio.text.toString())
                                    negocio_detalle_activity.putExtra("direccion",negocio.body.direccion)
                                    startActivity(negocio_detalle_activity)
                                }
                            }else if(negocio.error.equals("1")){
                                tMensajeBusqueda.text = negocio.messages
                            }

                        }

                        btnBuscarNegocio.isEnabled= true
                    }

                    override fun onFailure(call: Call<NegocioResponse>, t: Throwable) {
                        btnBuscarNegocio.isEnabled= true
                        TODO(t.toString() + "fff")
                    }

                })
            }
        }

        btnAgregarNegocio.setOnClickListener {
            startActivity(agregar_negocio_activity)
        }


    }

    private fun subirProductos(productos: List<Producto>) {
        var datosSubida = mutableMapOf<String,String>()
        datosSubida["email"] = usuario["email"].toString()
        datosSubida["medicion"] = prefs.getUsuario()["medicion"].toString()
        datosSubida["anio"] = prefs.getUsuario()["anio"].toString()
        datosSubida["mes"] = prefs.getUsuario()["mes"].toString()


        val productoSubirInput = ProductoSubirInput(productos,datosSubida)

        var service = ProductoService.create()
        var apiInterface = service.subir(productoSubirInput)
        apiInterface.enqueue( object : Callback<ProductoSubirResponse>
        {
            override fun onResponse(
                call: Call<ProductoSubirResponse>,
                response: Response<ProductoSubirResponse>
            ) {
                var todo = response.body()
                Toast.makeText(applicationContext,"Productos Enviados",Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<ProductoSubirResponse>, t: Throwable) {
                //btnBuscarMedicion.isEnabled= true
                TODO(t.toString() + "fff")
            }

        })
    }

    /*private fun subirProductos(productos: List<Producto>) {
        var datosSubida = object {
            "usuarioEmail" =
        }

        val productoSubirInput = ProductoSubirInput(productos)

        var service = ProductoService.create()
        var apiInterface = service.subir(productoSubirInput)
        apiInterface.enqueue( object : Callback<ProductoSubirResponse>
        {
            override fun onResponse(
                call: Call<ProductoSubirResponse>,
                response: Response<ProductoSubirResponse>
            ) {

            }
            override fun onFailure(call: Call<ProductoSubirResponse>, t: Throwable) {
                //btnBuscarMedicion.isEnabled= true
                TODO(t.toString() + "fff")
            }

        })
    }*/
}