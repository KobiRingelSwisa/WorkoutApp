package com.mykotlinapps.bodybuilder

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.caverock.androidsvg.R.attr.svg
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException

class SVGImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    private var svg: SVG? = null

    fun setSVG(resourceId: Int) {
        try {
            val inputStream = resources.openRawResource(resourceId)
            val svg = SVG.getFromInputStream(inputStream)
            val drawable = PictureDrawable(svg.renderToPicture())
            setImageDrawable(drawable)
            inputStream.close()  // Don't forget to close the InputStream
        } catch (e: SVGParseException) {
            e.printStackTrace()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (svg != null) {
            val aspectRatio = svg!!.documentAspectRatio
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = (width / aspectRatio).toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}