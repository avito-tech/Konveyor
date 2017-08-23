package com.avito.konveyor.blueprint

import android.support.v7.widget.RecyclerView

/**
 * Marker interface that represents an
 * abstraction over [RecyclerView.ViewHolder]
 */
interface ItemView {

    /**
     * Will be called on [RecyclerView.Adapter.onViewRecycled]
     *
     * This method is useful in those rare cases when you need to release resources/listeners/etc.
     * when view has been unbind
     */
    fun onUnbind() {}

}
