package com.example.auditoriafirst.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Categoria (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigo: String = "",
    val descripcion: String = "",
    val estado: String = ""
)