package com.avito.konveyor.data_source

class ListDataSource<T>(private val list: List<T>) : IterableDataSource<T> {

    override fun isEmpty() = list.isEmpty()

    override fun getCount(): Int {
        return list.count()
    }

    override fun getItem(position: Int): T {
        return list[position]
    }

    override fun iterator(): Iterator<T> = list.iterator()
}
