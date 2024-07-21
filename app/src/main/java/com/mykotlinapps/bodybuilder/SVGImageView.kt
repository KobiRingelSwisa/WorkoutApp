package com.mykotlinapps.bodybuilder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Picture
import android.util.AttributeSet
import android.widget.ImageView
import com.caverock.androidsvg.SVG

class SVGImageView(context: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    private var svg: SVG? = null

    fun setSVG(resourceId: Int) {
        svg = SVG.getFromResource(context, resourceId)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        svg?.let {
            val picture: Picture = it.renderToPicture()
            canvas.drawPicture(picture)
        }
    }
}
