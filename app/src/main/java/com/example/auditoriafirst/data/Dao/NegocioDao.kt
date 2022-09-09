package com.example.auditoriafirst.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.auditoriafirst.data.Entities.Negocio
import com.example.auditoriafirst.data.Entities.Usuario

@Dao
interface NegocioDao {
    @Query("SELECT * from Negocio where estadoVi=1 ORDER BY id DESC")
    suspend fun getAll(): List<Negocio>

    @Query("SELECT * from Negocio WHERE codigo_negocio= :codigo LIMIT 1")
    suspend fun get_codigo(codigo : String): Negocio

    @Query("SELECT * from Negocio WHERE id = :id")
    suspend fun get(id: Int): Negocio

    @Query("SELECT * from Negocio WHERE estadoVi = 2")
    suspend fun get_ocultos(): List<Negocio>

    @Query("UPDATE  Negocio set estadoVi=1 WHERE codigo_negocio= :cod_negocio")
    suspend fun update_recuperar(cod_negocio:String)

    @Query("UPDATE  Negocio set estadoVi=2 WHERE codigo_negocio= :cod_negocio")
    suspend fun update_archivar(cod_negocio:String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(negocio: Negocio)

    @Query("DELETE FROM Negocio")
    suspend fun borrarTodo()
}