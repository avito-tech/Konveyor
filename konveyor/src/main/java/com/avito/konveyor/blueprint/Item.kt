package com.avito.konveyor.blueprint

import android.support.v7.widget.RecyclerView

/**
 * Marker interface that represents
 * data associated with a certain [RecyclerView.Adapter.getItemViewType].
 */
interface Item {

    /**
     * Id that [RecyclerView.Adapter] will use in its methods.
     * For example, this will be returned in [RecyclerView.Adapter.getItemId]
     */
    val id: Long

}
