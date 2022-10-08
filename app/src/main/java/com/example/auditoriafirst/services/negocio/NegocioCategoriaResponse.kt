package com.example.auditoriafirst.services.negocio

import com.example.auditoriafirst.models.CategoriaModel
import com.example.auditoriafirst.models.MedicionModel
import com.example.auditoriafirst.models.NegocioModel
import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

data class NegocioCategoriaResponse(
    @SerializedName("status") var status: String,
    @SerializedName("error") var error: String,
    @SerializedName("body") var body: List<CategoriaModel>,
    @SerializedName("messages") var message: String
)

