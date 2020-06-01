package com.ssverma.covidtracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T, B : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseListAdapter<T, B>.BaseViewHolder>(diffCallback) {

    private lateinit var recyclerViewItemClickListener: (clickedView: View, T, position: Int) -> Unit
    private val clickableViews = arrayListOf<View>()

    fun setItemClickListener(recyclerViewItemClickListener: (clickedView: View, T, position: Int) -> Unit) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        onCreateViewHolder(binding)
        return BaseViewHolder(
            binding = binding
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemViewType(position: Int): Int {
        return providerItemLayout(providerItemLayout(position))
    }

    inner class BaseViewHolder(private val binding: B) : RecyclerView.ViewHolder(binding.root) {
        init {
            attachClickListeners(binding, this)
        }

        fun bind(item: T, position: Int) {
            binding.setVariable(BR.item, item)
            additionalBinding(binding, position, this)
            binding.executePendingBindings()
        }
    }

    open fun additionalBinding(
        binding: B,
        position: Int,
        viewHolder: BaseViewHolder
    ) {
        //To be override
    }

    open fun addClickableViews(binding: B, clickableViews: ArrayList<View>) {
        //To be override
    }

    open fun onCreateViewHolder(binding: B) {
        //To be override
    }

    private fun attachClickListeners(binding: B, viewHolder: BaseViewHolder) {
        clickableViews.clear()
        addClickableViews(binding, clickableViews)

        for (clickableView in clickableViews) {
            clickableView.setOnClickListener {
                if (::recyclerViewItemClickListener.isInitialized) {
                    recyclerViewItemClickListener(
                        it,
                        getItem(viewHolder.adapterPosition),
                        viewHolder.adapterPosition
                    )
                }
            }
        }

    }

    abstract fun providerItemLayout(position: Int): Int

}