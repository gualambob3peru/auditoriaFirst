package com.example.auditoriafirst.services.todo

import com.example.auditoriafirst.models.MedicionModel
import com.example.auditoriafirst.models.PersonalModel
import com.example.auditoriafirst.models.TodoModel
import com.google.gson.annotations.SerializedName

data class TodoResponse(
    @SerializedName("status") var status: String,
    @SerializedName("error") var error: String,
    @SerializedName("body") var body: TodoModel,
    @SerializedName("messages") var message: String
)

