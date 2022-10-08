package com.example.auditoriafirst.services.medicion

import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

 data class MedicionInput (
     @SerializedName("codigo") var codigo: String
     )

