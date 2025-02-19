package com.leandro.omieteste.ui.adapter

import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ItemAdicionarProdutoBinding
import com.leandro.omieteste.model.Produto

class ProdutoAdapter(val produtos : ArrayList<Produto>, val onDeleteClick: OnDeleteClick) : BaseAdapter<ItemAdicionarProdutoBinding, Produto>(produtos) {

    interface OnDeleteClick{
        fun onClick(produto: Produto)
    }

    override val layoutId: Int
        get() = R.layout.item_adicionar_produto

    override fun bind(binding: ItemAdicionarProdutoBinding, item: Produto) {
        binding.apply {
            produto = item
            imgTrash.setOnClickListener { onDeleteClick.onClick(item) }
            executePendingBindings()
        }
    }

    fun removerProduto(produto: Produto) {
        remove(produto)
    }

    fun addProduto(produto: Produto) {
        produtos.add(produto)
        updateData(produtos)
    }
}