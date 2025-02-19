package com.leandro.omieteste.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ActivityNovoPedidoBinding
import com.leandro.omieteste.ui.view.HintedTextWatcher

class NovoPedidoActivity : AppCompatActivity() {
    val binding : ActivityNovoPedidoBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_novo_pedido) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        binding.setCancelar { finish() }
        binding.setSalvar { verificaItens() }
        setEditTextWatchers()
    }

    private fun verificaItens() {

    }

    private fun verificaCampos() {

    }

    private fun setEditTextWatchers() {
        binding.editNome.addTextChangedListener(HintedTextWatcher(binding.editNome, binding.tilNome, getString(R.string.digite_nome_cliente)).addWatch())
        binding.editProduto.addTextChangedListener(HintedTextWatcher(binding.editProduto, binding.tilProduto, getString(R.string.digite_nome_produto)).addWatch())
        binding.editQuantidade.addTextChangedListener(HintedTextWatcher(binding.editQuantidade, binding.tilQuantidade, getString(R.string.quantidade_hint)).addWatch())
        binding.editValorUnitario.addTextChangedListener(HintedTextWatcher(binding.editValorUnitario, binding.tilValorUnitario, getString(R.string.label_zero_reais)).addWatch())

    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}