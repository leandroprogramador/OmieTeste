package com.leandro.omieteste.ui.pedido

import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ItemPedidosBinding
import com.leandro.omieteste.domain.model.PedidoQuantidadeProdutos
import com.leandro.omieteste.ui.util.BaseAdapter

class PedidoQuantidadeAdapter(pedidoQuantidadeProdutos: ArrayList<PedidoQuantidadeProdutos>, val onPedidoClick: OnPedidoClick) : BaseAdapter<ItemPedidosBinding, PedidoQuantidadeProdutos>(pedidoQuantidadeProdutos) {
    interface OnPedidoClick{
        fun onClick(pedidoQuantidadeProdutos: PedidoQuantidadeProdutos)
        fun onDelete(pedidoQuantidadeProdutos: PedidoQuantidadeProdutos)
    }

    override val layoutId: Int
        get() = R.layout.item_pedidos

    override fun bind(binding: ItemPedidosBinding, item: PedidoQuantidadeProdutos) {
        binding.apply {
            pedidoQuant = item
            binding.root.setOnClickListener { onPedidoClick.onClick(item) }
            binding.imgTrash.setOnClickListener { onPedidoClick.onDelete(item) }
            executePendingBindings()
        }
    }

    fun removePedido(pedidoQuantidadeProdutos: PedidoQuantidadeProdutos) {
        remove(pedidoQuantidadeProdutos)
    }
}