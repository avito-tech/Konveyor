package com.avito.konveyor

import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.avito.konveyor.ItemBinder.Builder
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.adapter.EmptyViewHolder
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ItemView
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.avito.konveyor.blueprint.ViewTypeProvider
import com.avito.konveyor.exception.ItemNotSupportedException
import com.avito.konveyor.exception.ViewTypeCollisionException
import com.avito.konveyor.validation.ValidationPolicy
import java.lang.Exception
import java.lang.IllegalArgumentException

/**
 * Current implementation binds together all of the abstractions provided by the library.
 * Usually you'd want to pass same instance of this class to [SimpleRecyclerAdapter] both as
 * an [ItemPresenter] and as an [ViewTypeProvider]
 *
 * You have to register all your with [Builder.registerItem] to form a scope of elements that
 * you expect to handle in the given [RecyclerView].
 *
 * By default current implementation will ignore collisions and unsupported data types.
 * It is possible to set eager validation with [Builder.setValidationPolicy], so exceptions will
 * be thrown in these cases, moreover, it is encouraged to do so in debug builds to avoid unexpected
 * behaviour in production
 */
class ItemBinder private constructor(
        private val itemBluePrints: List<ItemBlueprint<*, *>>,
        private val policy: ValidationPolicy) :
        ViewHolderBuilder<BaseViewHolder>, ViewTypeProvider, ItemPresenter<ItemView, Item> {

    override fun getItemViewType(item: Item): Int {
        checkCollisions(item)
        itemBluePrints
                .forEachIndexed { i, itemBluePrint ->
                    if (itemBluePrint.isRelevantItem(item)) return i
                }
        return stubViewTypeOrThrow(item)
    }

    private fun checkCollisions(item: Item) {
        if (policy.validateEagerly) {
            if (itemBluePrints.filter { it.isRelevantItem(item) }.count() > 1)
                throw ViewTypeCollisionException(item)
        }
    }

    private fun stubViewTypeOrThrow(item: Item): Int {
        return if (policy.validateEagerly) {
            throw ItemNotSupportedException(item)
        } else {
            UNKNOWN
        }
    }

    override fun buildViewHolder(parent: ViewGroup, viewType: Int, inflateFunc: (Int) -> View): BaseViewHolder? {
        val itemBluePrint = getItemBluePrintSafely(viewType) ?: return getStubViewHolderOrThrow(parent)
        return itemBluePrint.viewHolderProvider.creator(parent, inflateFunc(itemBluePrint.viewHolderProvider.layoutId))
    }

    private fun getStubViewHolderOrThrow(parent: ViewGroup): BaseViewHolder {
        return if (policy.validateEagerly) {
            throw IllegalArgumentException("View type is not supported")
        } else {
            EmptyViewHolder(parent)
        }
    }

    private fun getItemBluePrintSafely(viewType: Int): ItemBlueprint<*, *>? {
        return try {
            itemBluePrints[viewType]
        } catch (e: Exception) {
            null
        }
    }

    override fun bindView(view: ItemView, item: Item, position: Int) {
        getItemPresenter(item)?.bindView(view, item, position)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getItemPresenter(item: Item): ItemPresenter<ItemView, Item>? {
        val itemBluePrint = getItemBluePrintSafely(getItemViewType(item))
        if (itemBluePrint == null && policy.validateEagerly) throw ItemNotSupportedException(item)
        return itemBluePrint?.presenter as ItemPresenter<ItemView, Item>?
    }

    class Builder {

        private val itemBluePrints: MutableList<ItemBlueprint<*, *>> = mutableListOf()

        private var validationPolicy: ValidationPolicy = konveyorConfiguration.policy

        fun registerItem(bluePrint: ItemBlueprint<*, *>): Builder {
            itemBluePrints.add(bluePrint)
            return this
        }

        fun setValidationPolicy(validationPolicy: ValidationPolicy): Builder {
            this.validationPolicy = validationPolicy
            return this
        }

        fun build(): ItemBinder {
            return ItemBinder(itemBluePrints, validationPolicy)
        }

    }
}

@VisibleForTesting
const val UNKNOWN = -1