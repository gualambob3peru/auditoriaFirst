package com.example.auditoriafirst.services.negocio

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NegocioService {
    @Headers("Content-Type: application/json")
    @POST("negocio")
    fun negocio(@Body NegocioInput: NegocioInput): Call<NegocioResponse>

    @Headers("Content-Type: application/json")
    @POST("guardar")
    fun guardar(@Body NegocioInput: NegocioInput): Call<NegocioResponse>

    @Headers("Content-Type: application/json")
    @POST("categorias")
    fun categorias(@Body NegocioInput: NegocioInput): Call<NegocioCategoriaResponse>

    companion object {

        //var BASE_URL = "http://192.168.3.5/auditoria/public_auditoria/auditoria/api/negocio/"
        var BASE_URL = "http://damer.b3peru.com/api/negocio/"
        fun create() : NegocioService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(NegocioService::class.java)

        }
    }
}