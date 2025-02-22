package com.leandro.omieteste.ui.pedido

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ActivityNovoPedidoBinding
import com.leandro.omieteste.domain.model.Pedido
import com.leandro.omieteste.domain.model.Produto
import com.leandro.omieteste.ui.produto.ProdutoAdapter
import com.leandro.omieteste.ui.util.AlertUtil
import com.leandro.omieteste.ui.util.CurrencyTextWatcher
import com.leandro.omieteste.ui.util.HintedTextWatcher
import com.leandro.omieteste.ui.util.extensions.formatarMoeda
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class NovoPedidoActivity : AppCompatActivity(), ProdutoAdapter.OnDeleteClick {
    private val binding : ActivityNovoPedidoBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_novo_pedido) }
    private lateinit var adapter: ProdutoAdapter

    private val viewModel: PedidoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        criarEstruturaRecyclerView()
        carregaUltimoId()
        addObservable()
        binding.setCancelar { finish() }
        binding.setSalvar { salvar() }
        binding.setIncluirProduto { adicionarProduto() }
        setEditTextWatchers()
    }

    private fun addObservable() {
        viewModel.proximoId.observe(this) { ultimoPedidoId ->
            mostrarNumeroPedido(ultimoPedidoId)
        }
    }

    private fun mostrarNumeroPedido(ultimoPedidoId: Int?) {
        val proximoPedido = ultimoPedidoId!! + 1
        binding.txtNumeroPedido.text = String.format(Locale.getDefault(), "%04d", proximoPedido)
    }

    private fun carregaUltimoId() {
        viewModel.carregarProximoId()
    }

    private fun criarEstruturaRecyclerView() {
        binding.recyclerProdutos.layoutManager = LinearLayoutManager(this)
        adapter = ProdutoAdapter(arrayListOf(), this)
        binding.recyclerProdutos.adapter = adapter
    }

    private fun adicionarProduto() {
        if(verificaCampos()) {
            adapter.addProduto(
                Produto(
                binding.editProduto.text.toString(),
                binding.editQuantidade.text.toString().toInt(),
                CurrencyTextWatcher.unmask(binding.editValorUnitario.text.toString()).toDouble()))
            atualizarValoresTotais()
        } else {
            AlertUtil.showAlerta(this, getString(R.string.aviso), getString(R.string.preencha_campos_corretamente), getString(R.string.fechar)) {}
        }
    }

    @SuppressLint("SetTextI18n")
    private fun atualizarValoresTotais() {
        var quantidade = 0
        var valorTotal = 0.0
        adapter.getProdutos().forEach { produto ->
            quantidade += produto.quantidade
            valorTotal += produto.quantidade * produto.valorUnitario
        }

        binding.txtQuantidadeItens.text = quantidade.toString()
        binding.txtValorTotalPedido.text = valorTotal.formatarMoeda()
    }

    private fun salvar() {
        if(adapter.getCount() > 0) {
            val produtos = adapter.getProdutos()
            var valorTotal = 0.0
            produtos.forEach { produto ->
                valorTotal += produto.quantidade * produto.valorUnitario
            }

            viewModel.inserirPedidoComProdutos(
                Pedido(binding.editNome.text.toString(), valorTotal),produtos )
            AlertUtil.showAlerta(this, getString(R.string.aviso), getString(R.string.pedido_salvo_sucesso), getString(R.string.fechar), {finish()})
        } else {
            AlertUtil.showAlerta(this, getString(R.string.aviso), getString(R.string.add_produtos_erro), getString(R.string.fechar),{})
        }
    }

    private fun verificaCampos() : Boolean {
        var camposValidos = true
        if(binding.editNome.text.toString().isEmpty()) {
            showError(binding.tilNome, getString(R.string.erro_nome_cliente_vazio))
            camposValidos = false
        }
        if(binding.editProduto.text.toString().isEmpty()) {
            showError(binding.tilProduto, getString(R.string.erro_nome_produto_vazio))
            camposValidos = false
        }
        if(binding.editQuantidade.text.toString().isEmpty()) {
            showError(binding.tilQuantidade, getString(R.string.erro_quantidade_produto_invalida))
            camposValidos = false
        }
        else if(binding.editQuantidade.text.toString().toInt() == 0 ) {
            showError(binding.tilQuantidade, getString(R.string.erro_quantidade_produto_invalida))
            camposValidos = false
        }
        if(binding.editValorUnitario.text.toString().isEmpty()) {
            showError(binding.tilValorUnitario, getString(R.string.erro_valor_produto_invalida))
            camposValidos = false
        } else if(CurrencyTextWatcher.unmask(binding.editValorUnitario.text.toString()).toDouble() <=0){
            showError(binding.tilValorUnitario, getString(R.string.erro_valor_produto_invalida))
            camposValidos = false
        }
        return camposValidos
    }

    private fun showError(inputLayout: TextInputLayout, erro: String) {
        inputLayout.isErrorEnabled = true
        inputLayout.error = erro
    }

    private fun setEditTextWatchers() {
        binding.editNome.addTextChangedListener(HintedTextWatcher(binding.editNome, binding.tilNome, getString(R.string.digite_nome_cliente)).addWatch(){})
        binding.editProduto.addTextChangedListener(HintedTextWatcher(binding.editProduto, binding.tilProduto, getString(R.string.digite_nome_produto)).addWatch(){})
        binding.editQuantidade.addTextChangedListener(HintedTextWatcher(binding.editQuantidade, binding.tilQuantidade, "").addWatch(){ text ->
            if(text.isNotEmpty()) {
                calcValorTotalProduto()
            }
        })
        binding.editValorUnitario.addTextChangedListener(CurrencyTextWatcher(binding.editValorUnitario))
        binding.editValorUnitario.addTextChangedListener(HintedTextWatcher(binding.editValorUnitario, binding.tilValorUnitario, "").addWatch { text ->
            if(text.isNotEmpty()) {
                calcValorTotalProduto()
            }
        })


    }

    private fun calcValorTotalProduto() {
        val quantidade = binding.editQuantidade.text.toString().toInt()
        val valorUnitario = CurrencyTextWatcher.unmask(binding.editValorUnitario.text.toString()).toDouble()
        val valorTotalProduto = quantidade * valorUnitario
        binding.txtValorTotal.text = valorTotalProduto.formatarMoeda()

    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onClick(produto: Produto) {
        adapter.removerProduto(produto)
        atualizarValoresTotais()
    }
}