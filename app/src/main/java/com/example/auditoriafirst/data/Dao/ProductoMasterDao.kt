package com.example.auditoriafirst.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.auditoriafirst.data.Entities.Categoria
import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.data.Entities.ProductoMaster
import com.example.auditoriafirst.data.Entities.Usuario
import java.util.*

@Dao
interface ProductoMasterDao {
    @Query("SELECT * from ProductoMaster")
    suspend fun getAll(): List<ProductoMaster>

    @Query("SELECT * from ProductoMaster WHERE id = :id")
    suspend fun get(id: Int): ProductoMaster

    @Query("SELECT count(*) from ProductoMaster")
    suspend fun getCount(): Int

    @Query("SELECT * from ProductoMaster WHERE sku = :sku")
    suspend fun getsku(sku:String): ProductoMaster

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(producto: ProductoMaster)

    @Query("DELETE FROM ProductoMaster")
    suspend fun borrarAllAll()
}