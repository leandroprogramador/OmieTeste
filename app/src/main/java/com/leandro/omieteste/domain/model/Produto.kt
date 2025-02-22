package com.leandro.omieteste.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leandro.omieteste.ui.util.BaseAdapter
import com.leandro.omieteste.ui.util.extensions.formatarMoeda

@Entity(tableName = "produto")
data class Produto(
    @ColumnInfo(name = "nome_produto") val nomeProduto: String,
    @ColumnInfo(name = "quantidade") val quantidade: Int,
    @ColumnInfo(name = "valor_unitario") val valorUnitario: Double,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "produto_id") val produtoId: Long = 0
) : BaseAdapter.ListAdapterItem {
    fun calcValorTotal() : Double = quantidade * valorUnitario
    fun formatValorTotal() : String = calcValorTotal().formatarMoeda()
    fun formatValorUnitario() : String = valorUnitario.formatarMoeda()
    fun showQuantidade() : String = quantidade.toString()
}