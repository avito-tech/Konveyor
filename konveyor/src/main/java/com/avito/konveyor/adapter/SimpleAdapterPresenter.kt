package com.avito.konveyor.adapter

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ItemView
import com.avito.konveyor.blueprint.ViewTypeProvider
import com.avito.konveyor.data_source.DataSource
import com.avito.konveyor.data_source.ListDataSource

class SimpleAdapterPresenter(private val viewTypeProvider: ViewTypeProvider,
                             private val itemBinder: ItemPresenter<ItemView, Item>) : AdapterPresenter {

    private var dataSource: DataSource<out Item> = ListDataSource(emptyList())

    override fun onDataSourceChanged(dataSource: DataSource<out Item>) {
        this.dataSource = dataSource
    }

    override fun getCount(): Int = dataSource.count

    override fun bindView(view: ItemView, position: Int) = itemBinder.bindView(view, getItem(position), position)

    override fun getViewType(position: Int): Int {
        val item = dataSource.getItem(position)
        return viewTypeProvider.getItemViewType(item)
    }

    override fun getItemId(position: Int) = getItem(position).id

    private fun getItem(position: Int) = dataSource.getItem(position)

}