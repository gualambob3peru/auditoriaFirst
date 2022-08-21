package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.adapter.NegocioAdapter
import com.example.auditoriafirst.data.Entities.Negocio
import com.example.auditoriafirst.services.medicion.CategoriaInput
import com.example.auditoriafirst.services.medicion.CategoriaResponse
import com.example.auditoriafirst.services.medicion.MedicionService
import com.example.auditoriafirst.services.negocio.NegocioInput
import com.example.auditoriafirst.services.negocio.NegocioResponse
import com.example.auditoriafirst.services.negocio.NegocioService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class negocio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_negocio)

        var medicion = intent.getStringExtra("medicion")
        val txtNumMedicion = findViewById<TextView>(R.id.txtNumMedicion)
        txtNumMedicion.text = medicion

        val negocio_detalle_activity = Intent(this, NegocioDetalle::class.java)
        val negocio_categoria_activity = Intent(this, NegocioCategoria::class.java)

        val btnBuscarNegocio = findViewById<Button>(R.id.btnBuscarNegocio)
        val inputBuscarNegocio = findViewById<EditText>(R.id.inputBuscarNegocio)


        val medicionInput = CategoriaInput(medicion.toString())

        var service = MedicionService.create()
        var apiInterface = service.medicion(medicionInput)
        apiInterface.enqueue( object : Callback<CategoriaResponse>
        {
            override fun onResponse(
                call: Call<CategoriaResponse>,
                response: Response<CategoriaResponse>
            ) {
                var medicion = response.body()

                if (medicion != null) {
                    if(medicion.error.equals("0")){

                        lifecycleScope.launch{

                            var medi = medicion.body
                            if (medi != null) {
                                var arr_codigo : MutableList<String> = mutableListOf()
                                var arr_descripcion : MutableList<String> = mutableListOf()
                                var arr_negocio : MutableList<Negocio> = mutableListOf()


                                for(negocio in medi.negocios){
                                    arr_codigo.add(negocio.cod_negocio)
                                    arr_descripcion.add(negocio.direccion)
                                    var n_negocio= Negocio(negocio.id,negocio.cod_negocio,negocio.direccion,"2")
                                    arr_negocio.add(n_negocio)
                                }


                                var adapter = NegocioAdapter()
                                adapter.setList(arr_codigo,arr_descripcion,arr_negocio)

                                adapter.onItemClick = { pro,codigo_negocio,descripcion_negocio ->
                                    negocio_categoria_activity.putExtra("cod_negocio",codigo_negocio.text)
                                    negocio_categoria_activity.putExtra("descripcion_negocio",descripcion_negocio.text)
                                    startActivity(negocio_categoria_activity)
                                   /* prefs.setFechaVisita(pro.fecha_mes)
                                    intentoLocales.putExtra("idProgramacion",pro.id)
                                    negocio_categoria_activity.putExtra("tNombreRutaLocal",pro.r_descripcion)
                                    negocio_categoria_activity.putExtra("tFechaRutaLocal",pro.fecha_visita)
                                    startActivity(negocio_categoria_activity)*/
                                }

                                val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
                                var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNegocio)
                                recyclerView.layoutManager = linearLayoutManager
                                recyclerView.adapter = adapter
                            }


                        }
                    }else if(medicion.error.equals("1")){
                       // txtMensajeBusqueda.text = "Medición no encontrada"
                    }

                }

               // btnBuscarMedicion.isEnabled= true
            }

            override fun onFailure(call: Call<CategoriaResponse>, t: Throwable) {
                //btnBuscarMedicion.isEnabled= true
                TODO(t.toString() + "fff")
            }

        })

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
                                //txtMensajeBusqueda.text = "Medición no encontrada"
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
    }
}