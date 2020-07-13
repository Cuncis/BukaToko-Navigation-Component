package com.cuncis.bukatoko.util

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.cuncis.bukatoko.ui.cart.Item


@BindingAdapter("image")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}

@BindingAdapter("default_spinner_value")
fun defaultValueSpinner(spinner: Spinner, value: String) {
    val arrayList = arrayListOf<Int>()
    for (i in value.toInt()..50) {
        arrayList.add(i)
    }
    val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, arrayList)
    spinner.adapter = adapter
}