package com.avito.konveyor.blueprint

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.adapter.SimpleRecyclerAdapter

/**
 * Abstraction introduced to provide lazy [RecyclerView.ViewHolder] creation.
 * [SimpleRecyclerAdapter] will call this method during [RecyclerView.Adapter.onCreateViewHolder]
 */
interface ViewHolderBuilder<out T : BaseViewHolder> {
    fun buildViewHolder(parent: ViewGroup, viewType: Int, inflateFunc: (Int) -> View): T?

    class ViewHolderProvider<out T : BaseViewHolder>(@LayoutRes val layoutId: Int, val creator: (parent: ViewGroup, view: View) -> T)
}