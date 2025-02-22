package com.leandro.omieteste.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "pedido_produto",
    primaryKeys = ["pedido_id", "produto_id"],
    foreignKeys = [
        ForeignKey(entity = Pedido::class, parentColumns = ["pedido_id"], childColumns = ["pedido_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Produto::class, parentColumns = ["produto_id"], childColumns = ["produto_id"], onDelete = ForeignKey.CASCADE)
    ]
)
data class PedidoProduto(
    @ColumnInfo(name = "pedido_id") val pedidoId: Long,
    @ColumnInfo(name = "produto_id") val produtoId: Long
)
