package com.example.auditoriafirst.services.medicion

import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

 data class CategoriaProductoInput (
     @SerializedName("cod_categoria") var cod_categoria: String,
     @SerializedName("cod_negocio") var cod_negocio: String
     )

