package com.cuncis.bukatoko.ui.home.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.request.RequestOptions
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.persistence.Cart
import com.cuncis.bukatoko.data.local.persistence.CartViewModel
import com.cuncis.bukatoko.data.model.Data
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.ui.ShoppingActivity
import com.cuncis.bukatoko.util.Constants
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Utils
import com.cuncis.bukatoko.util.Utils.Companion.getCurrentDate
import com.cuncis.bukatoko.util.Utils.Companion.hideLoading
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showLoading
import com.cuncis.bukatoko.util.Utils.Companion.showView
import com.glide.slider.library.Animations.DescriptionAnimation
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.SliderTypes.DefaultSliderView
import kotlinx.android.synthetic.main.dialog_cart.view.*
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var productData: Product
    private lateinit var cartViewModel: CartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ShoppingActivity).supportActionBar?.title = "Detail Product"

        productData = arguments?.getParcelable(Constants.PRODUCT_DETAIL_EXTRA)!!
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        cartViewModel.getAllCarts().observe(requireActivity(), Observer {
            Log.d(TAG, "onViewCreated: Cart DB: $it")
        })

        view.apply {
            observeViewModel()
            initListener()
        }
    }

    private fun initListener() {
        btn_add_cart.setOnClickListener {
            val cart = Cart()
            cart.productName = productData.product
            cart.price = productData.price.toDouble()
            cart.currentDate = getCurrentDate()
            Log.d(TAG, "initListener: Data Temp: $cart")
            cartViewModel.insertCart(cart)
            showCartDialog()
        }
    }

    private fun showCartDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_cart, null)
        builder.setView(view)
        val dialog = builder.create()
        view.apply {
            btn_cart.setOnClickListener {
                findNavController().navigate(R.id.action_detailFragment_to_cartFragment)
                dialog.dismiss()
            }

            btn_pay.setOnClickListener {
                Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        dialog.show()
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