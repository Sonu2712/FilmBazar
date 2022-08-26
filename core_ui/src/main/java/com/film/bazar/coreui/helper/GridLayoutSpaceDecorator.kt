package com.film.bazar.coreui.helper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridLayoutSpaceDecorator(val padding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val tag = parent.getChildViewHolder(view).itemView.tag
        if (tag != null) {
            if (tag.toString() != OTHER) {
                val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
                val gridLayoutManager = parent.layoutManager as GridLayoutManager?
                val spanSize = layoutParams.spanSize.toFloat()
                val totalSpanSize = gridLayoutManager!!.spanCount.toFloat()
                val n = totalSpanSize / spanSize // num columns
                val c = layoutParams.spanIndex / spanSize // column index
                val leftPadding = padding * ((n - c) / n)
                outRect.left = leftPadding.toInt()

                when (tag.toString()) {
                    LINEAR -> {
                        val rightPadding = padding * ((c + 1) / n)
                        outRect.right = rightPadding.toInt()
                        outRect.bottom = padding
                    }
                    LINEARSMALL -> {
                        val rightPadding = padding * ((c + 1) / n)
                        outRect.right = rightPadding.toInt()
                        outRect.bottom = 2
                    }
                }
            }
        }
    }
}
