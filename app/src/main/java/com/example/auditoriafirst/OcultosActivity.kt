package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auditoriafirst.adapter.NegocioAdapter
import com.example.auditoriafirst.adapter.NegocioOcultoAdapter
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.data.Entities.Negocio
import com.example.auditoriafirst.shared.UsuarioApplication
import kotlinx.coroutines.launch

class OcultosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocultos)

        val db = AuditoriaDb(this)
        val intento1 = Intent(this, negocio::class.java)

        val btnAtras = findViewById<ImageView>(R.id.btnAtras)

        lifecycleScope.launch {
            var negocios = db.NegocioDao().get_ocultos();

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

                var adapter = NegocioOcultoAdapter()
                adapter.setList(arr_codigo,arr_descripcion,arr_negocio)

                adapter.onItemClick = { pro,codigo_negocio,descripcion_negocio ->
                    lifecycleScope.launch {
                        db.NegocioDao().update_recuperar(codigo_negocio.text.toString());

                        intento1.putExtra("medicion", UsuarioApplication.prefs.getUsuario()["medicion"])
                        startActivity(intento1)

                    }
                }

                val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
                var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNegocioArchivados)
                recyclerView.layoutManager = linearLayoutManager
                recyclerView.adapter = adapter

            }else{
                //No tiene ningun negocio
            }

        }

        btnAtras.setOnClickListener {
            intento1.putExtra("medicion", UsuarioApplication.prefs.getUsuario()["medicion"])
            startActivity(intento1)
        }


    }




}