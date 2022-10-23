package com.inhaproject.karaoke3.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DistanceItemDecorator(private val divValue: Int) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.top = divValue
        outRect.left = divValue
        outRect.bottom = divValue
        outRect.right = divValue
    }

}