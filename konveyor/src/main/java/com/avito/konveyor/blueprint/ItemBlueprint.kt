package com.avito.konveyor.blueprint

import androidx.recyclerview.widget.RecyclerView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ViewHolderBuilder.ViewHolderProvider
import com.avito.konveyor.exception.ViewTypeCollisionException

/**
 * Key entity that ties together Android world with a set of abstraction provided by Konveyor
 */
interface ItemBlueprint<in V : ItemView, in I : Item> {

    /**
     * Presenter associated with this view and data
     */
    val presenter: ItemPresenter<V, I>

    /**
     * Creates associated [RecyclerView.ViewHolder]
     *
     * @see ViewHolderBuilder
     */
    val viewHolderProvider: ViewHolderProvider<BaseViewHolder>

    /**
     * This method need to be implemented in order to build connection between given [Item]
     * and its [ItemBlueprint].
     *
     * Please mind, in the list of registered [ItemBlueprint]s every result of this function
     * has to be unique. If multiple registered [ItemBlueprint]s respond `true` to a given [Item],
     * [ViewTypeCollisionException] will be thrown.
     */
    fun isRelevantItem(item: Item): Boolean

}