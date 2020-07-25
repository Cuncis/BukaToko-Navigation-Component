package com.cuncis.bukatoko.util

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.TextView

object TextHelper {

    fun underlineIndicator(textView: TextView) {
        val content = SpannableString(textView.text.toString())
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        content.setSpan(StyleSpan(Typeface.BOLD), 0, content.length, 0)
        content.setSpan(ForegroundColorSpan(Color.BLUE), 0, content.length, 0)
        textView.text = content
    }

    fun restoreIndicator(textView: TextView) {
//        textView.setTextColor(Color.parseColor("000000"))
        textView.text = textView.text.toString()
    }

}