package com.leandro.omieteste.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseAdapter<BINDING : ViewDataBinding, T : BaseAdapter.ListAdapterItem>(var dataset : ArrayList<T>) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<BINDING>>()  {

    @get:LayoutRes
    abstract val layoutId : Int
    abstract fun bind(binding: BINDING, item : T)

    fun updateData(list : ArrayList<T>){
        this.dataset = list
        notifyDataSetChanged()
    }

    fun remove(item: T) {
        this.dataset.remove(item)
        notifyItemRemoved(dataset.indexOf(item))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BINDING> {
        val binder = DataBindingUtil.inflate<BINDING>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        return BaseViewHolder(binder)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BINDING>, position: Int) {
        bind(holder.binder, dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    interface ListAdapterItem
    class BaseViewHolder<BINDING : ViewDataBinding>(val binder : BINDING) : ViewHolder(binder.root)

}