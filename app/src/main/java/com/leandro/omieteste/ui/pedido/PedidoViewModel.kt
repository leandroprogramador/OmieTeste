package com.leandro.omieteste.ui.pedido

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandro.omieteste.data.repository.PedidoRepository
import com.leandro.omieteste.domain.model.Pedido
import com.leandro.omieteste.domain.model.PedidoComProdutos
import com.leandro.omieteste.domain.model.PedidoQuantidadeProdutos
import com.leandro.omieteste.domain.model.Produto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PedidoViewModel @Inject constructor(private val repository: PedidoRepository) : ViewModel() {


    private val _proximoId = MutableLiveData<Int>()
    val proximoId: LiveData<Int> get() = _proximoId

    private val _somaValoresTotais = MutableLiveData<Double?>()
    val somaValoresTotais: LiveData<Double?> get() = _somaValoresTotais

    private val _pedidosComQuantidadeDeProdutos = MutableLiveData<List<PedidoQuantidadeProdutos>>()
    val pedidosComQuantidadeDeProdutos: LiveData<List<PedidoQuantidadeProdutos>> get() = _pedidosComQuantidadeDeProdutos

    private val _deletarPedido = MutableLiveData<Boolean>()
    val deletarProduto : LiveData<Boolean> get() = _deletarPedido

    private val _pedidoComProdutos = MutableLiveData<PedidoComProdutos>()
    val pedidoComProdutos: LiveData<PedidoComProdutos> get() = _pedidoComProdutos
    

    fun carregarProximoId() {
        viewModelScope.launch {
            val id = repository.obterUltimoId()
            _proximoId.value = id
        }
    }


    fun carregarSomaValoresTotais() {
        viewModelScope.launch {
            val soma = repository.obterSomaValoresTotais()
            _somaValoresTotais.value = soma
        }
    }
    fun buscarPedidosComProdutos(pedidoId: Long) {
        viewModelScope.launch {
            _pedidoComProdutos.value = repository.buscarPedidoComProdutos(pedidoId)
        }

    }

    fun inserirPedidoComProdutos(pedido: Pedido, produtos: List<Produto>) {
        viewModelScope.launch {
            repository.inserirPedidoComProdutos(pedido, produtos)
        }
    }

    fun carregarPedidosComQuantidadeDeProdutos() {
        viewModelScope.launch {
            val pedidos = repository.obterPedidosComQuantidadeDeProdutos()
            _pedidosComQuantidadeDeProdutos.value = pedidos
        }
    }

    fun deletarProduto(pedidoId: Long){
        viewModelScope.launch {
            try {
                repository.deletarPedido(pedidoId)
                _deletarPedido.value = true
            } catch (ex : Exception) {
                _deletarPedido.value = false
            }
        }
    }
}