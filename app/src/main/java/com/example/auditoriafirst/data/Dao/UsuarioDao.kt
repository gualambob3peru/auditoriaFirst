package com.example.auditoriafirst.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.auditoriafirst.data.Entities.Usuario

@Dao
interface UsuarioDao {
    @Query("SELECT * from Usuario")
    suspend fun getAll(): List<Usuario>

    @Query("SELECT * from Usuario WHERE id = :id")
    suspend fun get(id: Int): Usuario

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usuario: List<Usuario>)

    @Query("DELETE FROM Usuario")
    suspend fun borrarTodo()
}