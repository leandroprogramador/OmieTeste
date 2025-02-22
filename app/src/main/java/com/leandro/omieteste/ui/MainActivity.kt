package com.leandro.omieteste.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ActivityMainBinding
import com.leandro.omieteste.ui.pedido.MeusPedidosActivity
import com.leandro.omieteste.ui.pedido.NovoPedidoActivity
import com.leandro.omieteste.ui.util.extensions.formatarMoeda
import com.leandro.omieteste.ui.pedido.PedidoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val binding : ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }
    private val viewModel: PedidoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        binding.setNovaVendaClick { abrirNovaVenda() }
        binding.setMeusPedidosClick { abrirMeusPedidos() }
        buscarValorTotal()
        addObservable()
    }

    private fun abrirMeusPedidos() {
        startActivity(Intent(this, MeusPedidosActivity::class.java))
    }

    private fun addObservable() {
        viewModel.somaValoresTotais.observe(this) { valorTotal ->
            showValor(valorTotal)
        }
    }

    private fun showValor(valorTotal: Double?) {
        binding.totalVendas = valorTotal.formatarMoeda()
        showContent()
    }

    private fun buscarValorTotal() {
        viewModel.carregarSomaValoresTotais()
    }

    private fun showContent() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.mainContent.visibility = View.VISIBLE
    }

    private fun abrirNovaVenda() {
        startActivity(Intent(this, NovoPedidoActivity::class.java))
    }

    override fun onRestart() {
        super.onRestart()
        buscarValorTotal()
    }
}
