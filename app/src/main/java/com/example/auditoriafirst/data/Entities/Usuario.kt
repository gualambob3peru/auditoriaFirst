package com.example.auditoriafirst.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val password: String,
    val email: String,
    val telefono: String,
    val created_at: String,
    val idCargo: String,
    val estado: String,
    val idTipoDocumento: String,
    val nroDocumento: String
)