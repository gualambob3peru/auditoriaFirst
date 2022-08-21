package com.example.auditoriafirst.services.medicion

import com.example.auditoriafirst.models.MedicionModel
import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

data class CategoriaResponse(
    @SerializedName("status") var status: String,
    @SerializedName("error") var error: String,
    @SerializedName("body") var body: MedicionModel,
    @SerializedName("messages") var message: String
)

