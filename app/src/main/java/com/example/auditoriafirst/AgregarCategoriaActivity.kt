package com.example.auditoriafirst

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.data.Entities.Producto
import kotlinx.coroutines.launch

class AgregarCategoriaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {
    //var languages = arrayOf("English", "French", "Spanish", "Hindi", "Russian", "Telugu", "Chinese", "German", "Portuguese", "Arabic", "Dutch", "Urdu", "Italian", "Tamil", "Persian", "Turkish", "Other")

    var spinner:Spinner? = null
    var textView_msg:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_categoria)
        val db = AuditoriaDb(this)

        var codigo_negocio = intent.getStringExtra("cod_negocio")
        val btnGuardarCategoria = findViewById<Button>(R.id.btnGuardarCategoria)
        val negocio_categoria_activity = Intent(this, NegocioCategoria::class.java)
        val btnAtras = findViewById<ImageView>(R.id.btnAtras)

        spinner = findViewById<Spinner>(R.id.cboCategoria)
        spinner!!.onItemSelectedListener = this
        var miCon = this

        lifecycleScope.launch{
            var categorias = db.CategoriaDao().getAll()
            var miArr = arrayOfNulls<String>(categorias.size)
            var miPos = arrayOfNulls<Int>(categorias.size)

            if(categorias !=null){
                for(ind in miArr.indices){
                    miArr[ind] = categorias[ind].descripcion
                }
            }

            val aa = ArrayAdapter(miCon, android.R.layout.simple_spinner_item, miArr)
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner!!.adapter = aa
        }

        btnGuardarCategoria.setOnClickListener {
            lifecycleScope.launch{
                var categorias = db.CategoriaDao().getAll()

                val text: String = spinner!!.selectedItem.toString()
                val miId = spinner!!.selectedItemId.toInt()

                var cod_cad = categorias[miId].codigo
                var desc_cad = categorias[miId].descripcion

                db.ProductoDao().insert(Producto(0,"",cod_cad,codigo_negocio.toString(),"","","","","","1",desc_cad))

                negocio_categoria_activity.putExtra("cod_negocio",codigo_negocio.toString())
                negocio_categoria_activity.putExtra("descripcion_negocio","")
                startActivity(negocio_categoria_activity)

            }
        }

        btnAtras.setOnClickListener {
            negocio_categoria_activity.putExtra("cod_negocio",codigo_negocio.toString())
            negocio_categoria_activity.putExtra("descripcion_negocio","")
            startActivity(negocio_categoria_activity)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }
}