package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.shared.UsuarioApplication
import kotlinx.coroutines.launch

class AgregarProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)
        val db = AuditoriaDb(this)
        var cod_categoria = intent.getStringExtra("cod_categoria")
        var cod_negocio = intent.getStringExtra("cod_negocio")
        var desc_categoria = intent.getStringExtra("desc_categoria")

        val categoria_producto_activity = Intent(this, CategoriaProductoActivity::class.java)

        val txtNombreProducto = findViewById<EditText>(R.id.txtNombreProducto)
        val btnGuardarProducto = findViewById<ImageView>(R.id.btnGuardarProducto)
        val btnAtras = findViewById<ImageView>(R.id.btnAtras)

        btnGuardarProducto.setOnClickListener {
            lifecycleScope.launch{
                var miNegocio = db.NegocioDao().get_codigo(cod_negocio.toString())

                var em = UsuarioApplication.prefs.getUsuario()["id"]
                var medd = UsuarioApplication.prefs.getUsuario()["medicion"]
                var cantProductos = db.ProductoDao().get_count()

                db.ProductoDao().insert(
                    Producto(
                        0,"S"+em+medd+cantProductos+(100..999).random(),
                        cod_categoria.toString(),
                        cod_negocio.toString(),
                        txtNombreProducto.text.toString(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        desc_categoria.toString(),
                        "0",
                        miNegocio.distrito,
                        miNegocio.zona,
                        miNegocio.canal
                    )
                )

                categoria_producto_activity.putExtra("descripcion_categoria",desc_categoria.toString())
                categoria_producto_activity.putExtra("cod_negocio",cod_negocio.toString())
                categoria_producto_activity.putExtra("cod_categoria",cod_categoria.toString())
                startActivity(categoria_producto_activity)
            }
        }

        btnAtras.setOnClickListener {
            categoria_producto_activity.putExtra("descripcion_categoria",desc_categoria.toString())
            categoria_producto_activity.putExtra("cod_negocio",cod_negocio.toString())
            categoria_producto_activity.putExtra("cod_categoria",cod_categoria.toString())
            startActivity(categoria_producto_activity)
        }


    }


}