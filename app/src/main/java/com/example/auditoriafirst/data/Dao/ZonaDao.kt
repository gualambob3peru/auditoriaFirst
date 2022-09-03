package com.example.auditoriafirst.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.auditoriafirst.data.Entities.Categoria
import com.example.auditoriafirst.data.Entities.Usuario
import com.example.auditoriafirst.data.Entities.Zona

@Dao
interface ZonaDao {
    @Query("SELECT * from Zona")
    suspend fun getAll(): List<Zona>

    @Query("SELECT * from Zona WHERE id = :id")
    suspend fun get(id: Int): Zona

    @Query("SELECT * from Zona WHERE descripcion = :descripcion")
    suspend fun get_descripcion(descripcion: String): Zona


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(zona: Zona)

    @Query("DELETE FROM Zona")
    suspend fun borrarTodo()
}