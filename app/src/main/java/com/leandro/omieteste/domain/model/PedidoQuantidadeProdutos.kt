package com.leandro.omieteste.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.leandro.omieteste.ui.util.BaseAdapter

data class PedidoQuantidadeProdutos(
    @Embedded
    val pedido: Pedido,
    @ColumnInfo(name = "quantidade_total")
    val quantidadeDeProdutos: Int
) : BaseAdapter.ListAdapterItem {
    fun showQuantidade() : String = quantidadeDeProdutos.toString()
}