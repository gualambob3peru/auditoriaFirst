package com.example.auditoriafirst.services.negocio

import com.example.auditoriafirst.models.MedicionModel
import com.example.auditoriafirst.models.NegocioModel
import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

data class NegocioResponse(
    @SerializedName("status") var status: String,
    @SerializedName("error") var error: String,
    @SerializedName("body") var body: NegocioModel = NegocioModel(),
    @SerializedName("messages") var messages: String
)

