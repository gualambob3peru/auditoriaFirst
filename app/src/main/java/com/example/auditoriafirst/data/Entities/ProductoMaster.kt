package com.example.auditoriafirst.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductoMaster (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sku: String = "",
    val descripcion: String = "",
    var estado: String = "1"
)