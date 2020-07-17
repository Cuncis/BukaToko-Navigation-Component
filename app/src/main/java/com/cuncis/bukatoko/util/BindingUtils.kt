package com.cuncis.bukatoko.util

import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.cuncis.bukatoko.data.model.Detail
import com.glide.slider.library.Animations.DescriptionAnimation
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.SliderTypes.DefaultSliderView


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

//@BindingAdapter("default_slider_image")
//fun loadImageSlider(slider: SliderLayout, data: Detail.Data) {
//    val imageList = ArrayList<String>()
//    val list = ArrayList<String>()
//
//    for (img in data.images) {
//        list.add(img.image)
//    }
//
//    imageList.addAll(list)
//
//    val sliderView = DefaultSliderView(slider.context)
//    for (i in 0 until imageList.size) {
//        sliderView.setProgressBarVisible(false)
//            .image(imageList[i])
//        slider.addSlider(sliderView)
//    }
//
//    slider.setPresetTransformer(SliderLayout.Transformer.Accordion)
//    slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
//    slider.setCustomAnimation(DescriptionAnimation())
//    slider.setDuration(4000)
//}