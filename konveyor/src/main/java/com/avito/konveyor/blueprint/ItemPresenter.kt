package com.avito.konveyor.blueprint

interface ItemPresenter<in V : ItemView, in I : Item> {

    fun bindView(view: V, item: I, position: Int)

}