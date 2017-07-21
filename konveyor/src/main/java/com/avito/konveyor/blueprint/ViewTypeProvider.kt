package com.avito.konveyor.blueprint

interface ViewTypeProvider {
    fun getItemViewType(item: Item): Int
}