package com.leandro.omieteste.ui.pedido

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ActivityMeusPedidosBinding
import com.leandro.omieteste.domain.model.Pedido
import com.leandro.omieteste.domain.model.PedidoQuantidadeProdutos
import com.leandro.omieteste.ui.util.AlertUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeusPedidosActivity : AppCompatActivity(), PedidoQuantidadeAdapter.OnPedidoClick {
    private var adapter:PedidoQuantidadeAdapter? = null
    private var pedidoParaDeletar: PedidoQuantidadeProdutos? = null
    val binding : ActivityMeusPedidosBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_meus_pedidos) }
    private val viewModel: PedidoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        getPedidos()
        addObservable()
    }

    private fun addObservable() {
        viewModel.pedidosComQuantidadeDeProdutos.observe(this) { pedidos ->
            mostrarPedidos(pedidos)
        }

        viewModel.deletarProduto.observe(this) { status ->
            if(status) {
                removerItemLista()
            } else {
                showAlertaErrorDeletar()
            }
        }
    }

    private fun removerItemLista() {
        adapter!!.removePedido(pedidoParaDeletar!!)
        pedidoParaDeletar = null
        updateView()
    }

    private fun updateView() {
        val itemCount = adapter!!.itemCount
        binding.totalPedidos = itemCount.toString()
        if(itemCount == 0) {
            nenhumPedido()
        }
    }

    private fun showAlertaErrorDeletar() {
        AlertUtil.showAlerta(this, getString(R.string.erro), getString(R.string.erro_deletar), getString(R.string.fechar), {})
    }

    private fun mostrarPedidos(pedidos: List<PedidoQuantidadeProdutos>?) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.mainContent.visibility = View.VISIBLE
        if(pedidos.isNullOrEmpty()) {
            nenhumPedido()
        } else {
            binding.totalPedidos = pedidos.size.toString()
            criarRecyclerView(pedidos)
        }

    }

    private fun nenhumPedido() {
        binding.totalPedidos = "0"
        binding.txtNenhumPedido.visibility = View.VISIBLE
        binding.recyclerPedidos.visibility = View.GONE
    }

    private fun criarRecyclerView(pedidos: List<PedidoQuantidadeProdutos>) {
        binding.recyclerPedidos.visibility = View.VISIBLE
        binding.txtNenhumPedido.visibility = View.GONE
        binding.recyclerPedidos.layoutManager = LinearLayoutManager(this)
        adapter = PedidoQuantidadeAdapter(ArrayList(pedidos), this)
        binding.recyclerPedidos.adapter = adapter
    }

    private fun getPedidos() {
        viewModel.carregarPedidosComQuantidadeDeProdutos()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onClick(pedidoQuantidadeProdutos: PedidoQuantidadeProdutos) {
        startActivity(Intent(this, PedidoDetalhesActivity::class.java).also {
            it.putExtra(Pedido.PEDIDO_ID, pedidoQuantidadeProdutos.pedido.id)
        })
    }

    override fun onDelete(pedidoQuantidadeProdutos: PedidoQuantidadeProdutos) {
        AlertUtil.showAlerta(this, getString(R.string.aviso), getString(R.string.deseja_excluir_pedido), getString(R.string.sim), {removerPedido(pedidoQuantidadeProdutos)}, getString(R.string.nao), {})
    }

    private fun removerPedido(pedidoQuantidadeProdutos: PedidoQuantidadeProdutos) {
        pedidoParaDeletar = pedidoQuantidadeProdutos
        viewModel.deletarProduto(pedidoQuantidadeProdutos.pedido.id)

    }
}