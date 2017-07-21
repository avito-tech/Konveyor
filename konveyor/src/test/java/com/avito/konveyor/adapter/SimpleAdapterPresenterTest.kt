package com.avito.konveyor.adapter

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ItemView
import com.avito.konveyor.blueprint.ViewTypeProvider
import com.avito.konveyor.data_source.ListDataSource
import com.avito.konveyor.util.Is
import com.avito.konveyor.util.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


@Suppress("IllegalIdentifier")
class SimpleAdapterPresenterTest {

    @Rule @JvmField val rule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var viewTypeProvider: ViewTypeProvider
    @Mock private lateinit var adapterViewPresenter: ItemPresenter<ItemView, Item>
    private lateinit var presenter: AdapterPresenter

    @Before
    fun setUp() {
        presenter = SimpleAdapterPresenter(viewTypeProvider, adapterViewPresenter)
    }

    @Test
    fun `count - returns 0 - by default`() = assertThat(presenter.getCount(), Is(0))

    @Test
    fun `bindView - delegates to AdapterViewPresenter - always`() {
        val item = createItem()
        val view = createItemView()
        changeDataSource(item)

        presenter.bindView(view, FIRST_ELEMENT)

        verify(adapterViewPresenter).bindView(view, item, FIRST_ELEMENT)
    }

    @Test
    fun `getViewType - delegates to ViewTypeProvider - always`() {
        val item = createItem()
        changeDataSource(item)
        whenever(viewTypeProvider.getItemViewType(any(item))).thenReturn(100)

        presenter.getViewType(FIRST_ELEMENT)

        verify(viewTypeProvider).getItemViewType(item)
    }

    private fun changeDataSource(vararg items: Item) {
        presenter.onDataSourceChanged(createDataSource(*items))
    }

    private fun createItemView() = object : ItemView {}

    private fun createItem(): Item {
        return object : Item {
            override val id = 0L

        }
    }

    private fun createDataSource(vararg elements: Item) = ListDataSource(elements.asList())
}

private const val FIRST_ELEMENT = 0