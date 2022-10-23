package com.inhaproject.karaoke3.ui.mypage.record

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.inhaproject.karaoke3.R

class RecordButton (
    context: Context,
    attrs: AttributeSet
): AppCompatImageButton(context, attrs) {

    fun updateIconWithState(state: State){
        when(state) {
            State.BEFORE_RECORDING -> {
                setImageResource(R.drawable.ic_record)
            }
            State.ON_RECORDING -> {
                setImageResource(R.drawable.ic_baseline_stop_24)
            }
            State.AFTER_RECORDING -> {
                setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
            State.ON_PLAYING -> {
                setImageResource(R.drawable.ic_baseline_stop_24)
            }
        }
    }
}