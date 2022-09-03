package com.example.auditoriafirst.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.auditoriafirst.data.Entities.*

@Dao
interface DistritoDao {
    @Query("SELECT * from Distrito")
    suspend fun getAll(): List<Distrito>

    @Query("SELECT * from Distrito WHERE id = :id")
    suspend fun get(id: Int): Distrito

    @Query("SELECT * from Distrito WHERE cod_zona = :cod_zona")
    suspend fun get_cod_zona(cod_zona: String): List<Distrito>

    @Query("SELECT * from Distrito WHERE descripcion = :descripcion")
    suspend fun get_descripcion(descripcion: String): Distrito


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(distrito: Distrito)

    @Query("DELETE FROM Distrito")
    suspend fun borrarTodo()
}