package com.example.auditoriafirst.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Producto (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sku: String = "",
    val cod_categoria: String = "",
    val cod_negocio : String ="",
    val descripcion: String = "",
    var compra : String = "0",
    var inventario : String  = "0",
    var precio : String = "0",
    var ve : String = "0",
    var estado: String = "",
    val desc_categoria : String = "",
    val vant : String ="0",
    val cod_distrito : String = "0",
    val cod_zona : String = "0",
    val cod_canal : String = "0"

)