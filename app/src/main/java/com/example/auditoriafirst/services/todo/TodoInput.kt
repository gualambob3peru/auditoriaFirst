package com.example.auditoriafirst.services.todo

import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

 data class TodoInput (
     @SerializedName("codigo") var codigo: String
     )

