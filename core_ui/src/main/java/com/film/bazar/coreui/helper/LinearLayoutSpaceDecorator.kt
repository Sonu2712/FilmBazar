package com.film.bazar.coreui.helper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearLayoutSpaceDecorator(val padding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = padding
        outRect.right = padding
        outRect.bottom = padding

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = padding
        }
    }
}
