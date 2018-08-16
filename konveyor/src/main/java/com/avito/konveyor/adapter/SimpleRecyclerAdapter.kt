package com.avito.konveyor.adapter

import android.support.annotation.LayoutRes
import android.support.v7.util.ListUpdateCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avito.konveyor.blueprint.ViewHolderBuilder
import java.lang.IllegalStateException

open class SimpleRecyclerAdapter(private val presenter: AdapterPresenter,
                                 private val holderProvider: ViewHolderBuilder<BaseViewHolder>) :
        RecyclerView.Adapter<BaseViewHolder>(), ListUpdateCallback {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return holderProvider.buildViewHolder(parent, viewType) { inflateView(it, parent) } ?: EmptyViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        presenter.bindView(holder, position)
    }

    override fun getItemCount(): Int = presenter.getCount()

    override fun getItemViewType(position: Int): Int = presenter.getViewType(position)

    override fun getItemId(position: Int) = presenter.getItemId(position)

    override fun onViewRecycled(holder: BaseViewHolder) {
        holder.onUnbind()
    }

    private fun inflateView(@LayoutRes layout: Int, parent: ViewGroup): View {
        if (layoutInflater == null) {
            this.layoutInflater = LayoutInflater.from(parent.context)
        }
        return layoutInflater?.inflate(layout, parent, false) ?: throw IllegalStateException()
    }

    override fun onInserted(position: Int, count: Int) {
        notifyItemRangeInserted(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        notifyItemRangeRemoved(position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        notifyItemRangeChanged(position, count, payload)
    }

}