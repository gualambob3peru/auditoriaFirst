package com.example.auditoriafirst.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Negocio (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val codigo_negocio: String = "",
    val descripcion: String = "",
    val vendedor : String = "",
    val telefono : String = "",
    val zona : String = "",
    val distrito : String ="",
    val canal : String ="",
    val nombre : String ="",
    val estado: String = "",
    val estadoVi : String = "1"
)