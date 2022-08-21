package com.example.auditoriafirst.services.medicion

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CategoriaService {
    @Headers("Content-Type: application/json")
    @POST("medicion")
    fun medicion(@Body MedicionInput: CategoriaInput): Call<CategoriaResponse>


    companion object {

        var BASE_URL = "http://192.168.3.5/auditoria/public_auditoria/auditoria/api/medicion/"

        fun create() : CategoriaService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(CategoriaService::class.java)

        }
    }
}