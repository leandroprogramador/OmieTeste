package com.leandro.omieteste.ui.produto

import com.leandro.omieteste.R
import com.leandro.omieteste.databinding.ItemAdicionarProdutoBinding
import com.leandro.omieteste.domain.model.Produto
import com.leandro.omieteste.ui.util.BaseAdapter

class ProdutoAdapter(private val produtos : ArrayList<Produto>, val onDeleteClick: OnDeleteClick) : BaseAdapter<ItemAdicionarProdutoBinding, Produto>(produtos) {

    interface OnDeleteClick{
        fun onClick(produto: Produto)
    }

    fun getProdutos() = produtos

    fun getCount() = produtos.size

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