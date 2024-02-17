package com.koai.wordsdk

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView

object WordSdk {

    fun createWordView(
        context: Context,
        text: String,
        textSize: Float,
        typeface: Typeface,
        backgroundDrawable: Drawable,
        textColor: Int
    ): TextView {
        val textView = TextView(context)
        val density = context.resources.displayMetrics.density
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rightMargin = 14
        layoutParams.height = (32 * density).toInt()
        layoutParams.width = (32 * density).toInt()
        layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        layoutParams.setGravity(Gravity.CENTER)
        textView.layoutParams = layoutParams
        textView.text = text
        textView.textSize = textSize
        textView.typeface = typeface
        textView.background = backgroundDrawable
        textView.setTextColor(textColor)
        textView.gravity = Gravity.CENTER
        textView.isClickable = true
        textView.isFocusable = true

        return textView
    }
}