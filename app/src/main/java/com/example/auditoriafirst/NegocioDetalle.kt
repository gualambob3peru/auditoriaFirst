package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.data.Entities.Negocio
import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.services.negocio.NegocioInput
import com.example.auditoriafirst.services.negocio.NegocioResponse
import com.example.auditoriafirst.services.negocio.NegocioService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NegocioDetalle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_negocio_detalle)

        val db = AuditoriaDb(this)

        val negocio_activity = Intent(this, negocio::class.java)

        var cod_negocio = intent.getStringExtra("cod_negocio")
        var direccion = intent.getStringExtra("direccion")

        val txt_codigo = findViewById<TextView>(R.id.txt_codigo)
        val txt_direccion = findViewById<TextView>(R.id.txt_direccion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        txt_codigo.text = cod_negocio
        txt_direccion.text = direccion


        btnGuardar.setOnClickListener {
            btnGuardar.isEnabled= false
            val negocioInput = NegocioInput(txt_codigo.text.toString())

            var service = NegocioService.create()
            var apiInterface = service.guardar(negocioInput)

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
                                //Limpiando como nuevo
                                db.ProductoDao().borrarAll(cod_negocio.toString())

                                db.NegocioDao().insert(Negocio(
                                    0,
                                    negocio.body.cod_negocio,
                                    negocio.body.direccion,
                                    "",
                                    "",
                                    negocio.body.cod_zona,
                                    negocio.body.cod_distrito,
                                    negocio.body.cod_canal
                                ))

                                //Productos
                                var categorias = negocio.body.categorias

                                for(cate in categorias){

                                    for(producto in cate.productos){
                                        db.ProductoDao().insert(
                                            Producto(
                                                0,
                                                producto.sku,
                                                producto.cod_categoria,
                                                negocio.body.cod_negocio,
                                                producto.descripcion,
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                cate.descripcion,
                                                producto.vant

                                            )
                                        )
                                    }

                                }


                                //////////////
                                negocio_activity.putExtra("medicion","2207")
                                startActivity(negocio_activity)


                            }
                        }else if(negocio.error.equals("1")){
                            //txtMensajeBusqueda.text = "Medici??n no encontrada"
                        }

                    }

                    btnGuardar.isEnabled= true
                }

                override fun onFailure(call: Call<NegocioResponse>, t: Throwable) {
                    btnGuardar.isEnabled= true
                    TODO(t.toString() + "fff")
                }

            })

        }
    }
}