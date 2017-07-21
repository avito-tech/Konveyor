package com.avito.konveyor.adapter

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemView
import com.avito.konveyor.data_source.DataSource


interface AdapterPresenter {

    fun bindView(view: ItemView, position: Int)

    fun getCount(): Int

    fun getViewType(position: Int): Int

    fun getItemId(position: Int): Long

    fun onDataSourceChanged(dataSource: DataSource<out Item>)

    fun isEmpty() = getCount() == 0
}
