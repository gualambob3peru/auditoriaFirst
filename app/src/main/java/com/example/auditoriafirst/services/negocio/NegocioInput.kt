package com.example.auditoriafirst.services.negocio

import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

 data class NegocioInput (
     @SerializedName("codigo") var codigo: String
     )

