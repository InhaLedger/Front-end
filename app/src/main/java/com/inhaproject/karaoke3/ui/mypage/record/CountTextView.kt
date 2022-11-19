package com.inhaproject.karaoke3.ui.mypage.record

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CountTextView(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs)  {

    private var startTimeStamp: Long = 0L

    private val countAction: Runnable = object: Runnable {
        override fun run() {
            val currentTimeStamp = SystemClock.elapsedRealtime()

            val countTimeSeconds = ((currentTimeStamp - startTimeStamp)/1000L).toInt()
            updateCount(countTimeSeconds)

            handler?.postDelayed(this, 1000L)

        }
    }

    fun startCount() {
        startTimeStamp = SystemClock.elapsedRealtime()
        handler?.post(countAction)
    }

    fun stopCount() {
        handler?.removeCallbacks(countAction)
    }

    private fun updateCount(countTimeSeconds: Int) {
        val minutes = countTimeSeconds / 60
        val seconds = countTimeSeconds % 60

        text ="%02d:%02d".format(minutes,seconds)
    }
}

