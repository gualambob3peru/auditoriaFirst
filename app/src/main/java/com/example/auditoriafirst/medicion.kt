package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.services.medicion.CategoriaInput
import com.example.auditoriafirst.services.medicion.CategoriaResponse
import com.example.auditoriafirst.services.medicion.MedicionService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class medicion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicion)

        val db = AuditoriaDb(applicationContext)
        val negocio_activity = Intent(this, negocio::class.java)

        val btnBuscarMedicion = findViewById<Button>(R.id.btnBuscarMedicion)
        val inputBuscarMedicion = findViewById<EditText>(R.id.inputBuscarMedicion)
        val tmensaje = findViewById<TextView>(R.id.tmensaje)

        btnBuscarMedicion.setOnClickListener {
            if(inputBuscarMedicion.text.toString().equals("") ){
                //tmensaje.text = "Debe rellenar todos los campos"
            }else{
                btnBuscarMedicion.isEnabled= false

                val medicionInput = CategoriaInput(inputBuscarMedicion.text.toString())

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
                                    negocio_activity.putExtra("medicion",inputBuscarMedicion.text.toString())
                                    startActivity(negocio_activity)
                                }
                            }else if(medicion.error.equals("1")){
                                tmensaje.text = "Medición no encontrada"
                            }

                        }else{
                            tmensaje.text = "Medición no encontrada"
                        }

                        btnBuscarMedicion.isEnabled= true
                    }

                    override fun onFailure(call: Call<CategoriaResponse>, t: Throwable) {
                        btnBuscarMedicion.isEnabled= true
                        TODO(t.toString() + "fff")
                    }

                })
            }
        }
    }
/*
    private fun getListNegocios(){
        lifecycleScope.launch{
            val db = AuditoriaDb(applicationContext)
            var arr_local : MutableList<String> = mutableListOf()
            var des_local : MutableList<String> = mutableListOf()
            var arr_id_programacion_local : MutableList<Negocio> = mutableListOf()
          //  val intentoLocales = Intent(applicationContext, LocalsActivity::class.java)

          val programacions = db.ProgramacionDao().getAll()

            for(pro in programacions){
                arr_local.add(pro.r_descripcion)
                des_local.add(pro.fecha_visita)
                arr_id_programacion_local.add(pro)
            }

            var adapter = CustomAdapter()
            adapter.setList(arr_local,des_local,programacions)

           adapter.onItemClick = { pro ->
                prefs.setFechaVisita(pro.fecha_mes)
                intentoLocales.putExtra("idProgramacion",pro.id)
                intentoLocales.putExtra("tNombreRutaLocal",pro.r_descripcion)
                intentoLocales.putExtra("tFechaRutaLocal",pro.fecha_visita)
                startActivity(intentoLocales)
            }

            val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
            var recyclerView = findViewById<RecyclerView>(R.id.recycleView)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = adapter
        }
    }*/
}