package com.example.auditoriafirst.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.auditoriafirst.data.Entities.Categoria
import com.example.auditoriafirst.data.Entities.Usuario

@Dao
interface CategoriaDao {
    @Query("SELECT * from Categoria")
    suspend fun getAll(): List<Categoria>

    @Query("SELECT * from Categoria WHERE id = :id")
    suspend fun get(id: Int): Categoria

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(categoria: Categoria)

    @Query("DELETE FROM Categoria")
    suspend fun borrarTodo()
}