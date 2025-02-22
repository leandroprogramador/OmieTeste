package com.leandro.omieteste.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leandro.omieteste.domain.model.Pedido
import com.leandro.omieteste.domain.model.PedidoProduto
import com.leandro.omieteste.domain.model.Produto
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PedidoDaoTest {
    private lateinit var database : AppDatabase
    private lateinit var pedidoDao: PedidoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        pedidoDao = database.pedidoDao()
    }

    @After()
    fun liberarBanco(){
        database.close()
    }

    @Test
    fun inserirPedidoERetornarID() = runBlocking {
        val pedidoTeste = Pedido(nomeCliente = "Cliente teste", valorTotal = 20.0, dataPedido = 1740248217)
        val pedidoId = pedidoDao.inserirPedido(pedidoTeste)
        assertNotNull(pedidoId)
        assertNotEquals(0, pedidoId)
    }

    @Test
    fun inserirProdutoERetornarID() = runBlocking {
        val produtoTeste = Produto(nomeProduto = "Produto Teste", 2,15.0 )
        val produtoId = pedidoDao.inserirProduto(produtoTeste)
        assertNotNull(produtoId)
        assertNotEquals(0, produtoId)
    }

    @Test
    fun obterUltimoIDPedido() = runBlocking {
        pedidoDao.inserirPedido(Pedido(nomeCliente = "Cliente teste 1", valorTotal = 20.0, 1740248217))
        pedidoDao.inserirPedido(Pedido(nomeCliente = "Cliente teste 2", valorTotal = 20.0, 1740248217))

        val ultimoId = pedidoDao.obterUltimoID()
        assertEquals(2, ultimoId)
    }

    @Test
    fun obterSomaTotalPedido() = runBlocking {
        pedidoDao.inserirPedido(Pedido(nomeCliente = "Cliente teste 1", valorTotal = 50.0, 1740248217))
        pedidoDao.inserirPedido(Pedido(nomeCliente = "Cliente teste 2", valorTotal = 20.0, 1740248217))
        pedidoDao.inserirPedido(Pedido(nomeCliente = "Cliente teste 3", valorTotal = 30.0, 1740248217))

        val somaPedido = pedidoDao.obterSomaValoresTotais()
        assertEquals(100.0, somaPedido)
    }

    @Test
    fun verificarSeDeletouPedido() = runBlocking {
        val idPedido = pedidoDao.inserirPedido(Pedido(nomeCliente = "Cliente teste 1", valorTotal = 50.0, 1740248217))
        val idProduto = pedidoDao.inserirProduto(Produto(nomeProduto = "Produto Teste", quantidade = 2, valorUnitario = 50.0))
        pedidoDao.relacionarPedidoProduto(PedidoProduto(idPedido, idProduto))
        pedidoDao.deletarPedidoComProdutos(idPedido)

        val pedido = pedidoDao.buscarPedidosComProdutos(idPedido)
        assertNull(pedido)
    }

    @Test
    fun obterPedidosQuantidade() = runBlocking {
        val idPedido = pedidoDao.inserirPedido(Pedido(nomeCliente = "Cliente teste 1", valorTotal = 500.0, 1740248217))
        val idProduto1 = pedidoDao.inserirProduto(Produto(nomeProduto = "Produto Teste", quantidade = 2, valorUnitario = 100.0))
        pedidoDao.relacionarPedidoProduto(PedidoProduto(idPedido, idProduto1))
        val idProduto2 = pedidoDao.inserirProduto(Produto(nomeProduto = "Produto Teste 2", quantidade = 5, valorUnitario = 60.0))
        pedidoDao.relacionarPedidoProduto(PedidoProduto(idPedido, idProduto2))

        val pedidoComProdutos = pedidoDao.obterPedidosComQuantidadeDeProdutos()
        assertEquals(7, pedidoComProdutos.first().quantidadeDeProdutos)
    }
}