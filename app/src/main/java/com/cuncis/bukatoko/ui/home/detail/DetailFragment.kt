package com.cuncis.bukatoko.ui.home.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Data
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.ui.ShoppingActivity
import com.cuncis.bukatoko.util.Constants
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Utils
import com.cuncis.bukatoko.util.Utils.Companion.hideLoading
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showLoading
import com.cuncis.bukatoko.util.Utils.Companion.showView
import com.glide.slider.library.Animations.DescriptionAnimation
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.SliderTypes.DefaultSliderView
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var productData: Product

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ShoppingActivity).supportActionBar?.title = "Detail Product"

        productData = arguments?.getParcelable(Constants.PRODUCT_DETAIL_EXTRA)!!
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        view.apply {
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        detailViewModel.getProductById(productData.id.toString()).observe(viewLifecycleOwner, Observer { detail ->
            setDetailData(detail.data)
        })
        detailViewModel.getMessage().observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        detailViewModel.onLoading().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                layout_progressBar.showLoading(requireActivity())
                linear_detail.hideView()
            } else {
                layout_progressBar.hideLoading(requireActivity())
                linear_detail.showView()
            }
        })
    }

    private fun setDetailData(detail: Data) {

        tv_name.text = detail.product
        tv_price.text = Utils.rupiah(detail.price)
        if (detail.description.isNullOrEmpty()) {
            tv_description.text = getString(R.string.lorem_ipsumm)
        } else {
            tv_description.text = productData.description
        }

        val imageList = ArrayList<String>()
        val list = ArrayList<String>()

        for (img in detail.images) {
            list.add(img.image)
        }

        imageList.addAll(list)

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder_error)

        val sliderView = DefaultSliderView(requireContext())
        for (i in 0 until imageList.size) {
            sliderView.setRequestOption(requestOptions)
                .setProgressBarVisible(false)
                .image(imageList[i])
            slider.addSlider(sliderView)
            Log.d(TAG, "setDetailData: ${imageList[i]}")
        }

        slider.setPresetTransformer(SliderLayout.Transformer.Accordion)
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        slider.setCustomAnimation(DescriptionAnimation())
        slider.setDuration(4000)
    }


}