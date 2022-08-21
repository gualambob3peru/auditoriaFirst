package com.example.auditoriafirst.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Producto (
    @PrimaryKey(autoGenerate = true)
    val id: String = "",
    val sku: String = "",
    val cod_categoria: String = "",
    val descripcion: String = "",
    val estado: String = ""
)