package com.example.auditoriafirst.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.auditoriafirst.data.Entities.Categoria
import com.example.auditoriafirst.data.Entities.Producto
import com.example.auditoriafirst.data.Entities.Usuario
import java.util.*

@Dao
interface ProductoDao {
    @Query("SELECT * from Producto")
    suspend fun getAll(): List<Producto>

    @Query("SELECT * from Producto WHERE cod_negocio = :cod_negocio AND cod_categoria = :cod_categoria AND sku!=''")
    suspend fun getAllProductosNego(cod_negocio:String , cod_categoria:String ): List<Producto>

    @Query("SELECT * from Producto WHERE cod_negocio = :cod_negocio  AND sku!=''")
    suspend fun getAllProductos_negocio(cod_negocio:String ): List<Producto>

    @Query("SELECT * from Producto WHERE id = :id")
    suspend fun get(id: Int): Producto

    @Query("SELECT * from Producto WHERE cod_negocio = :cod_negocio AND cod_categoria = :cod_categoria  AND sku!=''")
    suspend fun getAllProductos_categoria(cod_negocio:String , cod_categoria:String ): List<Producto>

    @Query("SELECT id,cod_categoria as codigo,desc_categoria as descripcion,1 estado from Producto WHERE cod_negocio = :cod_negocio GROUP BY cod_categoria,desc_categoria")
    suspend fun getCategorias_negocio(cod_negocio:String ): List<Categoria>

    @Query("UPDATE Producto SET compra=:compra , inventario=:inventario,precio=:precio, ve=:ve WHERE cod_negocio = :cod_negocio AND cod_categoria = :cod_categoria")
    suspend fun update(cod_negocio:String , cod_categoria:String ,compra:String,inventario:String,precio:String,ve:String)

    @Query("UPDATE Producto SET  compra=:compra , inventario=:inventario,precio=:precio, ve=:ve WHERE sku=:sku AND cod_negocio = :cod_negocio AND cod_categoria = :cod_categoria")
    suspend fun update_sku(sku:String,cod_negocio:String , cod_categoria:String ,compra:String,inventario:String,precio:String,ve:String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(producto: Producto)

    @Query("DELETE FROM Producto WHERE cod_negocio = :cod_negocio AND cod_categoria = :cod_categoria")
    suspend fun borrarTodo(cod_negocio:String , cod_categoria:String )

    @Query("DELETE FROM Producto WHERE cod_negocio = :cod_negocio")
    suspend fun borrarAll(cod_negocio:String )

    @Query("DELETE FROM Producto")
    suspend fun borrarAllAll()
}