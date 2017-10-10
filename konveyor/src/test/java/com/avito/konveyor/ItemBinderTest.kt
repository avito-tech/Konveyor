package com.avito.konveyor

import android.view.View
import android.view.ViewGroup
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.adapter.EmptyViewHolder
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ItemView
import com.avito.konveyor.blueprint.ViewHolderBuilder.ViewHolderProvider
import com.avito.konveyor.exception.ItemNotSupportedException
import com.avito.konveyor.exception.ViewTypeCollisionException
import com.avito.konveyor.util.Is
import com.avito.konveyor.util.instanceOf
import com.avito.konveyor.validation.ValidationPolicy
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.*

@Suppress("IllegalIdentifier")
class ItemBinderTest {

    @Rule @JvmField val rule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var itemViewPresenter: ItemPresenter<ItemView, Item>
    @Mock private lateinit var itemView: ItemView

    private val random = Random(System.currentTimeMillis())


    @Test fun `getItemViewType - returns corresponding viewType - when item is supported`() {
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = true))
                .build()

        assertThat(itemBinder.getItemViewType(randomItem()), Is(SUPPORTED_ITEM))
    }

    @Test(expected = ItemNotSupportedException::class)
    fun `getItemViewType - throws - when in eager validation is turned on and item is not supported`() {
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = false))
                .setValidationPolicy(eagerValidationPolicy())
                .build()

        itemBinder.getItemViewType(randomItem())
    }

    @Test(expected = ViewTypeCollisionException::class)
    fun `getItemViewType - throws collision exception - when in eager validation is turned on and item is can be handled by multiple presenters`() {
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = true))
                .registerItem(buildItemBluePrint(isRelevant = true))
                .setValidationPolicy(eagerValidationPolicy())
                .build()

        itemBinder.getItemViewType(randomItem())
    }

    @Test fun `getItemViewType - returns UnknownViewType - when in eager validation is turned off and item is not supported`() {
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = false))
                .setValidationPolicy(tolerantValidationPolicy())
                .build()

        assertThat(itemBinder.getItemViewType(randomItem()), Is(UNKNOWN))
    }

    @Test fun `bindView - bind view through corresponding presenter - when item is supported`() {
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = true))
                .build()
        val item = randomItem()

        itemBinder.bindView(itemView, item, 0)

        verify(itemViewPresenter).bindView(itemView, item, 0)
    }

    @Test(expected = ItemNotSupportedException::class)
    fun `bindView - throws - when eager validation is turned on and item is not supported`() {
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = false))
                .setValidationPolicy(eagerValidationPolicy())
                .build()

        itemBinder.bindView(itemView, randomItem(), 0)
    }

    @Test fun `bindView - does nothing - when item is not supported and eager validation is turned off`() {
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = false))
                .setValidationPolicy(tolerantValidationPolicy())
                .build()

        itemBinder.bindView(itemView, randomItem(), 0)

        verify(itemViewPresenter, never()).bindView(any(), any(), any())
    }

    @Test fun `buildViewHolder - returns corresponding ViewHolder to given ViewType - when ViewType is supported`() {
        val view = mock<View>()
        val viewHolder = randomViewHolder(view)
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = true, viewHolder = viewHolder))
                .setValidationPolicy(eagerValidationPolicy())
                .build()
        val viewGroup = mock<ViewGroup>()

        val createdVh = itemBinder.buildViewHolder(viewGroup, SUPPORTED_ITEM, { view })

        assertThat(createdVh, Is(viewHolder))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `buildViewHolder - throws - when eager validation is turned on and ViewType is not supported`() {
        val view = mock<View>()
        val viewHolder = randomViewHolder(view)
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = false, viewHolder = viewHolder))
                .setValidationPolicy(eagerValidationPolicy())
                .build()
        val viewGroup = mock<ViewGroup>()

        itemBinder.buildViewHolder(viewGroup, UNSUPPORTED_ITEM, { view })
    }

    @Test
    fun `buildViewHolder - returns EmptyViewHolder - when eager validation is turned off and ViewType is not supported`() {
        val view = mock<View>()
        val viewHolder = randomViewHolder(view)
        val itemBinder = ItemBinder.Builder()
                .registerItem(buildItemBluePrint(isRelevant = false, viewHolder = viewHolder))
                .setValidationPolicy(tolerantValidationPolicy())
                .build()
        val viewGroup = mock<ViewGroup>()

        val createdVh = itemBinder.buildViewHolder(viewGroup, UNSUPPORTED_ITEM, { view })

        assertThat(createdVh, instanceOf<EmptyViewHolder>())
    }


    private fun randomItem() = object : Item {
        override val id: Long = random.nextLong()
    }

    private fun randomViewHolder(view: View = mock<View>()): BaseViewHolder {
        return object : BaseViewHolder(view) {}
    }

    private fun eagerValidationPolicy(): ValidationPolicy {
        return object : ValidationPolicy {
            override var validateEagerly = true
        }
    }

    private fun tolerantValidationPolicy(): ValidationPolicy {
        return object : ValidationPolicy {
            override var validateEagerly = false
        }
    }

    private fun buildItemBluePrint(isRelevant: Boolean, viewHolder: BaseViewHolder = randomViewHolder()) =
            object : ItemBlueprint<ItemView, Item> {

                override val presenter = itemViewPresenter

                override val viewHolderProvider = ViewHolderProvider(random.nextInt(), { _, _ -> viewHolder })

                override fun isRelevantItem(item: Item) = isRelevant

            }

}

private const val SUPPORTED_ITEM = 0
private const val UNSUPPORTED_ITEM = 1