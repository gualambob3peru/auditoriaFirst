package com.example.auditoriafirst.models

import com.google.gson.annotations.SerializedName

class MedicionModel {
    var id: String = ""
    var codigo: String = ""
    var negocios : List<NegocioModel> = listOf()
    var estado: String = ""
}