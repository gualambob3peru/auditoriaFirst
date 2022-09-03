package com.example.auditoriafirst.models

import com.google.gson.annotations.SerializedName

class CategoriaModel {
    var id: String = ""
    var codigo: String = ""
    var descripcion: String = ""
    var productos : List<ProductoModel> = listOf()
}