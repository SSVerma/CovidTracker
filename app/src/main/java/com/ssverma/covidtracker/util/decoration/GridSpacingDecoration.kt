package com.ssverma.covidtracker.util.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssverma.covidtracker.util.ViewUtil


class GridSpacingDecoration(
    private val spanCount: Int,
    private var spacing: Int = 16,
    private val includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    init {
        spacing = ViewUtil.dpToPx(spacing.toFloat())
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right =
                spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}