package com.example.auditoriafirst.models

import com.google.gson.annotations.SerializedName

class NegocioModel {
    var id: String = ""
    var cod_negocio: String = ""
    var direccion: String = ""
    var categorias : List<CategoriaModel> = listOf()
    var cod_distrito : String = "0"
    var cod_canal : String = "0"
    var cod_zona : String = "0"
}