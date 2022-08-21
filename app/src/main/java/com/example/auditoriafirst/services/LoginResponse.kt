package com.example.auditoriafirst.services

import com.example.auditoriafirst.models.PersonalModel
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") var status: String,
    @SerializedName("error") var error: String,
    @SerializedName("user") var user: List<PersonalModel>,
    @SerializedName("messages") var message: String
)

