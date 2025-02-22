package com.leandro.omieteste.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leandro.omieteste.domain.model.Pedido
import com.leandro.omieteste.domain.model.PedidoProduto
import com.leandro.omieteste.domain.model.Produto

@Database(
    entities = [Pedido::class, Produto::class, PedidoProduto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun pedidoDao() : PedidoDao

    companion object {
        val DATABASE = "minhaloja_database"

    }

}