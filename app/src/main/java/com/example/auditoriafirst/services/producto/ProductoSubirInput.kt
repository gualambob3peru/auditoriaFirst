package com.example.auditoriafirst.services.medicion

import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName
import java.util.*

data class ProductoSubirInput (
     @SerializedName("productos") var productos: List<Producto>,
     @SerializedName("datosSubida") var datosSubida: MutableMap<String,String>
     )

