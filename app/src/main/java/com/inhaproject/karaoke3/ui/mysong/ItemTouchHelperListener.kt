package com.inhaproject.karaoke3.ui.mysong

interface ItemTouchHelperListener {
    fun onItemMove(from_position: Int, to_position: Int): Boolean
    fun onItemSwipe(position: Int)
}