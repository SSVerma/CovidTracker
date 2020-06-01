package com.ssverma.covidtracker.util.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssverma.covidtracker.util.ViewUtil

class FirstItemPaddingDecoration(
    private var spanCount: Int = 1,
    private var spacing: Int = 16
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
        val position = parent.getChildAdapterPosition(view)
        if (position < spanCount) {
            outRect.left = spacing
        }
    }
}