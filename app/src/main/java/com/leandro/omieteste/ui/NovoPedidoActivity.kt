package com.leandro.omieteste.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ActivityNovoPedidoBinding
import com.leandro.omieteste.ui.view.CurrencyTextWatcher
import com.leandro.omieteste.ui.view.HintedTextWatcher

class NovoPedidoActivity : AppCompatActivity() {
    val binding : ActivityNovoPedidoBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_novo_pedido) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        binding.setCancelar { finish() }
        binding.setSalvar { verificaItens() }
        binding.setIncluirProduto { verificaCampos() }
        setEditTextWatchers()
    }

    private fun verificaItens() {

    }

    private fun verificaCampos() : Boolean {
        var camposValidos = true
        if(binding.editNome.text.toString().isEmpty()) {
            binding.tilNome.isErrorEnabled = true
            binding.tilNome.error = getString(R.string.erro_nome_cliente_vazio)
        }
        return camposValidos
    }

    private fun setEditTextWatchers() {
        binding.editNome.addTextChangedListener(HintedTextWatcher(binding.editNome, binding.tilNome, getString(R.string.digite_nome_cliente)).addWatch())
        binding.editProduto.addTextChangedListener(HintedTextWatcher(binding.editProduto, binding.tilProduto, getString(R.string.digite_nome_produto)).addWatch())
        binding.editQuantidade.addTextChangedListener(HintedTextWatcher(binding.editQuantidade, binding.tilQuantidade, getString(R.string.quantidade_hint)).addWatch())
        binding.editValorUnitario.addTextChangedListener(CurrencyTextWatcher(binding.editValorUnitario))
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