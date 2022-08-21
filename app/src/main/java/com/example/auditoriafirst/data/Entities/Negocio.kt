package com.example.auditoriafirst.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Negocio (
    @PrimaryKey(autoGenerate = true)
    val id: String = "",
    val codigo_negocio: String = "",
    val descripcion: String = "",
    val estado: String = ""
)