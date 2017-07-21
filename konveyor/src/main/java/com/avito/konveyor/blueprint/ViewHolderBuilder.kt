package com.avito.konveyor.blueprint

import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import com.avito.konveyor.adapter.BaseViewHolder

interface ViewHolderBuilder<out T : BaseViewHolder> {
    fun buildViewHolder(parent: ViewGroup, viewType: Int, inflateFunc: (Int) -> View): T?

    class ViewHolderProvider<out T : BaseViewHolder>(@LayoutRes val layoutId: Int, val creator: (parent: ViewGroup, view: View) -> T)
}