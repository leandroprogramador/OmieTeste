package com.leandro.omieteste.model

import com.leandro.omieteste.ui.adapter.BaseAdapter

data class Produto(val nomeProduto : String, val quantidade : Int, val valorUnitario : Double) : BaseAdapter.ListAdapterItem{
    fun calcValorTotal() : Double = valorUnitario * quantidade

    fun formatValorUnitario() : String = valorUnitario.toString()
}