package com.example.auditoriafirst.services.medicion

import com.example.auditoriafirst.models.MedicionModel
import com.example.auditoriafirst.models.PersonalModel
import com.example.auditoriafirst.models.ProductoModel
import com.google.gson.annotations.SerializedName

data class CategoriaProductoResponse(
    @SerializedName("status") var status: String,
    @SerializedName("error") var error: String,
    @SerializedName("body") var body: List<ProductoModel>,
    @SerializedName("messages") var message: String
)

