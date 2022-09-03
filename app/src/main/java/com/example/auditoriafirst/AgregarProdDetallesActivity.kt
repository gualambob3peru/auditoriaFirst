package com.example.auditoriafirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.auditoriafirst.services.LoginInput
import com.example.auditoriafirst.services.LoginResponse
import com.example.auditoriafirst.services.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarProdDetallesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_prod_detalles)

        var sku = intent.getStringExtra("sku")



       /* val loginInput = LoginInput(txtEmail.text.toString(), txtPassword.text.toString())
        var service = LoginService.create()
        var apiInterface = service.validar(loginInput)
        apiInterface.enqueue( object : Callback<LoginResponse>
        {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {}

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                TODO(t.toString() + "fff")
            }

        })*/

    }
}