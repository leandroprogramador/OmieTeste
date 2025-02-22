package com.leandro.omieteste.data.repository

import com.leandro.omieteste.data.local.PedidoDao
import com.leandro.omieteste.domain.model.Pedido
import com.leandro.omieteste.domain.model.PedidoComProdutos
import com.leandro.omieteste.domain.model.PedidoProduto
import com.leandro.omieteste.domain.model.PedidoQuantidadeProdutos
import com.leandro.omieteste.domain.model.Produto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PedidoRepository @Inject constructor(private val pedidoDao: PedidoDao){

    suspend fun obterSomaValoresTotais(): Double? {
        return pedidoDao.obterSomaValoresTotais()
    }

    suspend fun obterPedidosComQuantidadeDeProdutos(): List<PedidoQuantidadeProdutos> {
        return pedidoDao.obterPedidosComQuantidadeDeProdutos()
    }

    suspend fun deletarPedido(pedidoId: Long) {
        pedidoDao.deletarPedidoComProdutos(pedidoId)
    }

    suspend fun buscarPedidoComProdutos(pedidoId : Long) : PedidoComProdutos? {
        return pedidoDao.buscarPedidosComProdutos(pedidoId)
    }

    suspend fun inserirPedidoComProdutos(pedido : Pedido, produtos : List<Produto>) {
        val pedidoId = pedidoDao.inserirPedido(pedido)
        produtos.forEach { produto->
            val produtoId = pedidoDao.inserirProduto(produto)
            pedidoDao.relacionarPedidoProduto(PedidoProduto(pedidoId, produtoId))
        }
    }

    suspend fun obterUltimoId() : Int {
        return pedidoDao.obterUltimoID()
    }

}