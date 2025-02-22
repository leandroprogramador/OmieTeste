package com.leandro.omieteste.ui.pedido

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ActivityPedidoDetalhesBinding
import com.leandro.omieteste.domain.model.Pedido
import com.leandro.omieteste.domain.model.PedidoComProdutos
import com.leandro.omieteste.domain.model.Produto
import com.leandro.omieteste.ui.produto.ProdutoPedidoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PedidoDetalhesActivity : AppCompatActivity() {
    val binding : ActivityPedidoDetalhesBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_pedido_detalhes) }
    val pedidoId : Long by lazy { intent.getLongExtra(Pedido.PEDIDO_ID, 0L) }
    private val viewModel: PedidoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        getProdutos()
        addObservable()
    }

    private fun addObservable() {
        viewModel.pedidoComProdutos.observe(this) { pedidoComProduto ->
            mostrarPedidoProduto(pedidoComProduto)
        }
    }

    private fun criarLista(produtos: List<Produto>) {
        binding.recyclerProdutos.layoutManager = LinearLayoutManager(this)
        binding.recyclerProdutos.adapter = ProdutoPedidoAdapter(ArrayList(produtos))
    }

    private fun mostrarPedidoProduto(pedidoComProduto: PedidoComProdutos?) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.mainContent.visibility = View.VISIBLE
        binding.pedido = pedidoComProduto!!.pedido
        criarLista(pedidoComProduto.produtos)
    }

    private fun getProdutos() {
        viewModel.buscarPedidosComProdutos(pedidoId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}