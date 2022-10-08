package com.example.auditoriafirst.data.Database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.auditoriafirst.data.Dao.*
import com.example.auditoriafirst.data.Entities.*

@Database(entities = [

    Usuario::class,
    Producto::class,
    ProductoMaster::class,
    Negocio::class,
    Categoria::class,
    Canal::class,
    Zona::class,
    Distrito::class

                     ],
    version = 1,exportSchema=false)
abstract class AuditoriaDb : RoomDatabase()  {

    abstract fun UsuarioDao(): UsuarioDao
    abstract fun ProductoDao(): ProductoDao
    abstract fun ProductoMasterDao(): ProductoMasterDao
    abstract fun NegocioDao(): NegocioDao
    abstract fun CategoriaDao(): CategoriaDao
    abstract fun CanalDao(): CanalDao
    abstract fun ZonaDao(): ZonaDao
    abstract fun DistritoDao(): DistritoDao


    companion object {
        @Volatile private var instance: AuditoriaDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AuditoriaDb::class.java, "auditoria")
            .build()
    }
}