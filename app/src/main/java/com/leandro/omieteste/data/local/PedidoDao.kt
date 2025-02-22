package com.leandro.omieteste.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.leandro.omieteste.domain.model.Pedido
import com.leandro.omieteste.domain.model.PedidoComProdutos
import com.leandro.omieteste.domain.model.PedidoProduto
import com.leandro.omieteste.domain.model.PedidoQuantidadeProdutos
import com.leandro.omieteste.domain.model.Produto

@Dao
interface PedidoDao {

    @Insert
    suspend fun inserirPedido(pedido: Pedido) : Long

    @Insert
    suspend fun inserirProduto(produto: Produto) : Long

    @Insert
    suspend fun relacionarPedidoProduto(pedidoProduto: PedidoProduto)

    @Transaction
    @Query("SELECT * FROM pedido WHERE pedido_id = :pedidoId")
    fun buscarPedidosComProdutos(pedidoId : Long) : LiveData<PedidoComProdutos>

    @Query("SELECT SUM(valor_total) FROM Pedido")
    suspend fun obterSomaValoresTotais(): Double?


    @Transaction
    @Query("SELECT * FROM produto p INNER JOIN pedido_produto pp ON p.produto_id = pp.produto_id WHERE pp.pedido_id = :pedidoId")
    fun buscarProdutosPorPedido(pedidoId: Long): LiveData<List<Produto>>

    @Query("SELECT COALESCE(MAX(pedido_id), 0) FROM Pedido")
    suspend fun obterUltimoID(): Int

    @Transaction
    @Query("SELECT p.pedido_id, p.nome_cliente, p.data_pedido, p.valor_total, COALESCE(SUM(pr.quantidade), 0) AS quantidade_total FROM pedido p LEFT JOIN pedido_produto pp ON p.pedido_id = pp.pedido_id LEFT JOIN produto pr ON pp.produto_id = pr.produto_id GROUP BY p.pedido_id, p.nome_cliente, p.data_pedido, p.valor_total")
    suspend fun obterPedidosComQuantidadeDeProdutos(): List<PedidoQuantidadeProdutos>


    @Transaction
    @Query("DELETE FROM pedido_produto WHERE pedido_id = :pedidoId")
    suspend fun deletarRelacaoPedidoProduto(pedidoId: Long)

    @Transaction
    @Query("DELETE FROM produto WHERE produto_id IN (SELECT produto_id FROM pedido_produto WHERE pedido_id = :pedidoId)")
    suspend fun deletarProdutosDoPedido(pedidoId: Long)

    @Transaction
    @Query("DELETE FROM pedido WHERE pedido_id = :pedidoId")
    suspend fun deletarPedido(pedidoId: Long)

    @Transaction
    suspend fun deletarPedidoComProdutos(pedidoId: Long) {
        deletarProdutosDoPedido(pedidoId)
        deletarPedido(pedidoId)
        deletarRelacaoPedidoProduto(pedidoId)
    }
}