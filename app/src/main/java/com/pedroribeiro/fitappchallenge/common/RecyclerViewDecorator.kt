package com.pedroribeiro.fitappchallenge.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDecorator(private val height: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = height
            }
            bottom = height
        }
    }
}