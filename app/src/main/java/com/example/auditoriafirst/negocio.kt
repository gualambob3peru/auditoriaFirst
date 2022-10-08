package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.adapter.NegocioAdapter
import com.example.auditoriafirst.components.negocio.NegocioResetDialog
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.data.Entities.Negocio
import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.services.medicion.*
import com.example.auditoriafirst.services.negocio.NegocioInput
import com.example.auditoriafirst.services.negocio.NegocioResponse
import com.example.auditoriafirst.services.negocio.NegocioService
import com.example.auditoriafirst.shared.UsuarioApplication
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
        val login_activity = Intent(this, MainActivity::class.java)
        val ocultos_activity = Intent(this, OcultosActivity::class.java)

        val btnBuscarNegocio = findViewById<Button>(R.id.btnBuscarNegocio)
        val inputBuscarNegocio = findViewById<EditText>(R.id.inputBuscarNegocio)
        val tMensajeBusqueda = findViewById<TextView>(R.id.tMensajeBusqueda)
        val btnAgregarNegocio = findViewById<ImageView>(R.id.btnAgregarNegocio)
        val btnReset = findViewById<ImageView>(R.id.btnReset)
        val btnMostratOculto = findViewById<ImageView>(R.id.btnMostratOculto)
        val intento1 = Intent(this, negocio::class.java)

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
                        var elNegocio = db.NegocioDao().get_codigo(miNegocio.codigo_negocio)
                        subirProductos(productos,elNegocio)
                    }
                }

                adapter.onItemArchivarClick = { miNegocio->
                    lifecycleScope.launch{
                        db.NegocioDao().update_archivar(miNegocio.codigo_negocio);

                        intento1.putExtra("medicion", UsuarioApplication.prefs.getUsuario()["medicion"])
                        startActivity(intento1)
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

        btnReset.setOnClickListener {

            var dialog = NegocioResetDialog()
            dialog.show(supportFragmentManager,"ResetDialog")


        }

        btnMostratOculto.setOnClickListener {
            startActivity(ocultos_activity)
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
                        Toast.makeText(applicationContext,"Revise su conexión",Toast.LENGTH_SHORT).show()
                        //TODO(t.toString() + "fff")
                    }

                })
            }
        }

        btnAgregarNegocio.setOnClickListener {
            startActivity(agregar_negocio_activity)
        }


    }

    private fun subirProductos(productos: List<Producto>, miNegocio: Negocio) {
        var datosSubida = mutableMapOf<String,String>()
        datosSubida["email"] = usuario["email"].toString()
        datosSubida["medicion"] = prefs.getUsuario()["medicion"].toString()
        datosSubida["anio"] = prefs.getUsuario()["anio"].toString()
        datosSubida["mes"] = prefs.getUsuario()["mes"].toString()
        datosSubida["cod_negocio"] = miNegocio.codigo_negocio
        datosSubida["direccion_negocio"] = miNegocio.descripcion
        datosSubida["nombre_negocio"] = miNegocio.nombre
        datosSubida["telefono_negocio"] = miNegocio.telefono
        datosSubida["vendedor_negocio"] = miNegocio.vendedor

        datosSubida["cod_zona"] = miNegocio.zona
        datosSubida["cod_canal"] = miNegocio.canal
        datosSubida["cod_distrito"] = miNegocio.distrito




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
                Toast.makeText(applicationContext,"Revise su conexión",Toast.LENGTH_SHORT).show()
                //btnBuscarMedicion.isEnabled= true
                //TODO(t.toString() + "fff")
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