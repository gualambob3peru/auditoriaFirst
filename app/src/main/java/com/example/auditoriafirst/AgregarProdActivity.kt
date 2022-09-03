package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.data.Entities.ProductoMaster
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class AgregarProdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_prod)

        val db = AuditoriaDb(this)
        var cod_categoria = intent.getStringExtra("cod_categoria")
        var cod_negocio = intent.getStringExtra("cod_negocio")
        var desc_categoria = intent.getStringExtra("desc_categoria")

        var btnBuscarProd = findViewById<Button>(R.id.btnBuscarProd)
        val agregar_prod_detalle_activity = Intent(this, AgregarProdDetallesActivity::class.java)
        val txtsku = findViewById<EditText>(R.id.txtsku)
        val btnAtras = findViewById<ImageView>(R.id.btnAtras)
        val txtNombreProd = findViewById<TextView>(R.id.txtNombreProd)
        var txtSkuProd = findViewById<TextView>(R.id.txtSkuProd)
        val btnGuardar = findViewById<ImageView>(R.id.btnGuardar)
        val categoria_producto_activity = Intent(this, CategoriaProductoActivity::class.java)


        var miSku = ""
        var miDescripcion = ""
        var buscado = 0

        btnBuscarProd.setOnClickListener {
            lifecycleScope.launch {
                buscado = 1
                var miProducto = db.ProductoMasterDao().getsku(txtsku.text.toString())

                if(miProducto!=null){
                    buscado = 1
                    txtNombreProd.text = miProducto.descripcion
                    txtSkuProd.text = miProducto.sku
                    miSku = miProducto.sku
                    miDescripcion = miProducto.descripcion
                }else{
                    buscado = 0
                    txtNombreProd.text = "No encontrado"
                    txtSkuProd.text = ""
                    miSku= ""
                    miDescripcion = ""
                }

            }

        }

        btnGuardar.setOnClickListener {
            lifecycleScope.launch {
                if(buscado==1){
                    var miNegocio = db.NegocioDao().get_codigo(cod_negocio.toString())

                    db.ProductoDao().insert(
                            Producto(
                                0,
                                miSku,
                                cod_categoria.toString(),
                                cod_negocio.toString(),
                                miDescripcion,
                                "",
                                "",
                                "",
                                "",
                                "1",
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
                }else{

                }
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