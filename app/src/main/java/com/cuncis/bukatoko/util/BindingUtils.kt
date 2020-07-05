package com.cuncis.bukatoko.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*


@BindingAdapter("image")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}

fun String.rupiah(): String {
    if (this.isNotEmpty()) {
        val numberFormat: NumberFormat = NumberFormat.getInstance(Locale.GERMANY)
        return numberFormat.format(numberFormat)
    }
    return ""
}