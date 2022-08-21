package com.example.auditoriafirst.services

import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

 data class LoginInput (
     @SerializedName("email") var email: String,
     @SerializedName("password") var password: String
     )

