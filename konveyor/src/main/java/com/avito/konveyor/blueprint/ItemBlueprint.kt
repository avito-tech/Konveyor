package com.avito.konveyor.blueprint

import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ViewHolderBuilder.ViewHolderProvider

interface ItemBlueprint<in V : ItemView, in I : Item> {

    val presenter: ItemPresenter<V, I>

    val viewHolderProvider: ViewHolderProvider<BaseViewHolder>

    fun isRelevantItem(item: Item): Boolean

}