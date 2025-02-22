package com.leandro.omieteste.ui.produto

import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ItemProdutoPedidoBinding
import com.leandro.omieteste.domain.model.Produto
import com.leandro.omieteste.ui.util.BaseAdapter

class ProdutoPedidoAdapter(val produtos : ArrayList<Produto>) : BaseAdapter<ItemProdutoPedidoBinding, Produto>(produtos){


    override val layoutId: Int
        get() = R.layout.item_produto_pedido

    override fun bind(binding: ItemProdutoPedidoBinding, item: Produto) {
        binding.apply {
            produto = item
            executePendingBindings()
        }
    }


}