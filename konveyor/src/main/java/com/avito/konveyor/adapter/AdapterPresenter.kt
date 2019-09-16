package com.avito.konveyor.adapter

import androidx.recyclerview.widget.RecyclerView
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemView
import com.avito.konveyor.data_source.DataSource

/**
 * Base abstraction over [RecyclerView.Adapter]
 */
interface AdapterPresenter {

    /**
     * Will be called on [RecyclerView.Adapter.onBindViewHolder]
     */
    fun bindView(view: ItemView, position: Int)

    /**
     * Will be called on [RecyclerView.Adapter.getItemCount]
     */
    fun getCount(): Int

    /**
     * Will be called on [RecyclerView.Adapter.getItemViewType]
     */
    fun getViewType(position: Int): Int

    /**
     * Will be called on [RecyclerView.Adapter.getItemId]
     */
    fun getItemId(position: Int): Long

    /**
     * Method to notify [AdapterPresenter] that data has been changed
     *
     * <em>Note:</em> if you are using [SimpleRecyclerAdapter] you still need to call
     * [RecyclerView.Adapter.notifyDataSetChanged] in order to notify your adapter about changes
     */
    fun onDataSourceChanged(dataSource: DataSource<out Item>)

    fun isEmpty() = getCount() == 0
}