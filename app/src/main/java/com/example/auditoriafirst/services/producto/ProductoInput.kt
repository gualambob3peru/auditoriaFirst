package com.example.auditoriafirst.services.medicion

import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

 data class ProductoInput (
     @SerializedName("codigo") var codigo: String
     )

