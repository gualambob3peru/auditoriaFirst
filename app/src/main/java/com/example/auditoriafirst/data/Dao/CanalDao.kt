package com.example.auditoriafirst.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.auditoriafirst.data.Entities.Canal
import com.example.auditoriafirst.data.Entities.Categoria
import com.example.auditoriafirst.data.Entities.Usuario
import com.example.auditoriafirst.data.Entities.Zona

@Dao
interface CanalDao {
    @Query("SELECT * from Canal")
    suspend fun getAll(): List<Canal>

    @Query("SELECT * from Canal WHERE id = :id")
    suspend fun get(id: Int): Canal

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(canal: Canal)

    @Query("DELETE FROM Canal")
    suspend fun borrarTodo()
}