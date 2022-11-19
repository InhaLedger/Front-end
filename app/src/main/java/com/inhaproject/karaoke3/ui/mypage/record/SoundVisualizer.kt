package com.inhaproject.karaoke3.ui.mypage.record

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.inhaproject.karaoke3.R

class SoundVisualizer (
    context: Context,
    attrs: AttributeSet? = null) : View(context, attrs)
{

    var onRequestCurrentAmplitude: (() -> Int)? = null

    private val amplitudePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.color_orange)
        strokeWidth = Line_Width
        strokeCap = Paint.Cap.ROUND
    }
    private var drawingWidth: Int = 0
    private var drawingHeight: Int = 0
    private var drawingAmplitudes: List<Int> = emptyList()
    private var isReplay : Boolean = false
    private var replayingPosition: Int = 0


    private val visualizeRepeatAction: Runnable = object : Runnable {
        override fun run() {
            // 여기가 amplitude 가져오는 부분 !!!!!!! 핵심
            if(!isReplay) {
                val currentAmplitude = onRequestCurrentAmplitude?.invoke() ?: 0
                // Amplitude , Draw
                drawingAmplitudes = listOf(currentAmplitude) + drawingAmplitudes
            } else {
                replayingPosition++
            }
            invalidate()

            handler?.postDelayed(this, Action_Interval)
        }


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawingWidth = w
        drawingHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return

        val centerY = drawingHeight / 2f
        var offsetX = drawingWidth.toFloat()

        drawingAmplitudes
            .let{ amplitudes ->
                if(isReplay){
                    amplitudes.takeLast(replayingPosition)
                } else {
                    amplitudes
                }
            }

            .forEach { amplitude ->
            val lineLength = (amplitude / MAX_AMPLITUDE * drawingHeight * 0.8F) * 25

            offsetX -= Line_Space
            if(offsetX < 0) return@forEach

            canvas.drawLine(offsetX,
                centerY - lineLength / 2F,
                offsetX,
                centerY + lineLength / 2f,
                amplitudePaint
            )
        }


    }

    fun startVisualizing(isReplay: Boolean) {
        this.isReplay = isReplay
        handler?.post(visualizeRepeatAction)
    }

    fun stopVisualizing() {
        handler?.removeCallbacks(visualizeRepeatAction)
    }

    companion object{
        private const val Line_Width = 10F
        private const val Line_Space = 15F
        private const val Action_Interval = 20L
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
    }
}