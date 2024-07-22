package com.mykotlinapps.bodybuilder.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.mykotlinapps.bodybuilder.R
import kotlin.math.min

class StrengthScoreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var score: Int = 0
    private var minScore: Int = 200
    private var maxScore: Int = 300

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 40f
        color = Color.LTGRAY
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 40f
        color = ContextCompat.getColor(context, R.color.dark_red)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textSize = 80f
        textAlign = Paint.Align.CENTER
    }

    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.DKGRAY
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }

    private val rect = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(centerX, centerY) - 50f

        // Draw background arc
        rect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        canvas.drawArc(rect, 135f, 270f, false, backgroundPaint)

        // Draw progress arc
        val sweepAngle = 270f * (score - minScore) / (maxScore - minScore)
        canvas.drawArc(rect, 135f, sweepAngle, false, progressPaint)

        // Draw score text
        canvas.drawText(score.toString(), centerX, centerY + 30f, textPaint)

    }

    fun setScore(newScore: Int) {
        score = newScore.coerceIn(minScore, maxScore)
        invalidate()
    }
}