package com.leandro.omieteste.domain.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PedidoComProdutos(
    @Embedded val pedido: Pedido,
    @Relation(
        parentColumn = "pedido_id",
        entityColumn = "produto_id",
        associateBy = Junction(PedidoProduto::class)
    )
    val produtos: List<Produto>
)