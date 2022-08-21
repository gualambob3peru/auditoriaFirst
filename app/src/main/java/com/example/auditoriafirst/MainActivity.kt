package com.example.auditoriafirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.auditoriafirst.data.Database.AuditoriaDb
import com.example.auditoriafirst.data.Entities.Usuario
import com.example.auditoriafirst.services.LoginInput
import com.example.auditoriafirst.services.LoginResponse
import com.example.auditoriafirst.services.LoginService
import com.example.auditoriafirst.shared.UsuarioApplication.Companion.prefs
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AuditoriaDb(this)

        val botonLogin = findViewById<Button>(R.id.btnLogin)
        val txtEmail = findViewById<TextView>(R.id.txtEmail)
        val txtPassword = findViewById<TextView>(R.id.txtPassword)
        val tmensaje = findViewById<TextView>(R.id.tmensaje)
        val intento1 = Intent(this, negocio::class.java)

        botonLogin.setOnClickListener {

            if(txtEmail.text.toString().equals("") || txtPassword.text.toString().equals("") ){
                tmensaje.text = "Debe rellenar todos los campos"
            }else{
                botonLogin.isEnabled= false

                val loginInput = LoginInput(txtEmail.text.toString(), txtPassword.text.toString())
                var service = LoginService.create()
                var apiInterface = service.validar(loginInput)
                apiInterface.enqueue( object : Callback<LoginResponse>
                {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        var usuario = response.body()

                        if (usuario != null) {
                            if(usuario.error.equals("0")){

                                lifecycleScope.launch{
                                    db.UsuarioDao().borrarTodo()

                                    val u = usuario.user[0]

                                    var miUsuario = Usuario(
                                        u.id.toInt(),
                                        u.nombres,
                                        u.apellidoPaterno,
                                        u.apellidoMaterno,
                                        u.password,
                                        u.email,
                                        u.telefono,
                                        u.created_at,
                                        u.idCargo,
                                        u.estado,
                                        u.idTipoDocumento,
                                        u.nroDocumento
                                    )

                                    db.UsuarioDao().insert(listOf(miUsuario))

                                    //Guardando en shared usuario
                                    var sharedUsuario = emptyMap<String,String>().toMutableMap()
                                    sharedUsuario["id"] = u.id.toString()
                                    sharedUsuario["nombres"] = u.nombres
                                    sharedUsuario["apellidoPaterno"] = u.apellidoPaterno
                                    sharedUsuario["apellidoMaterno"] = u.apellidoMaterno
                                    sharedUsuario["password"] = u.password
                                    sharedUsuario["email"] = u.email
                                    sharedUsuario["telefono"] = u.telefono
                                    sharedUsuario["created_at"] = u.created_at
                                    sharedUsuario["idCargo"] = u.idCargo
                                    sharedUsuario["estado"] = u.estado
                                    sharedUsuario["idTipoDocumento"] = u.idTipoDocumento
                                    sharedUsuario["nroDocumento"] = u.nroDocumento

                                    prefs.setUsuario(sharedUsuario)

                                    intento1.putExtra("medicion","2207")
                                    startActivity(intento1)
                                }
                            }else if(usuario.error.equals("1")){
                                tmensaje.text = usuario.message
                            }

                        }

                        botonLogin.isEnabled= true
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        botonLogin.isEnabled= true
                        TODO(t.toString() + "fff")
                    }

                })
            }



        }
    }
}