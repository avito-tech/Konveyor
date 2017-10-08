package com.example.konveyor.items

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class VerticalSpaceItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                       state: RecyclerView.State) {
        outRect.bottom = offset
        outRect.left = offset
        outRect.right = offset
    }
}