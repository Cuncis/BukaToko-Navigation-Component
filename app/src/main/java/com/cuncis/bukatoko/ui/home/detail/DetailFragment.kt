package com.cuncis.bukatoko.ui.home.detail

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
import androidx.navigation.fragment.navArgs
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Cart
import com.cuncis.bukatoko.ui.cart.CartViewModel
import com.cuncis.bukatoko.data.model.Detail
import com.cuncis.bukatoko.ui.ShoppingActivity
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Dialogs.dialogCustomCart
import com.cuncis.bukatoko.util.Status
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
import org.koin.android.ext.android.inject


class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var cartViewModel: CartViewModel

    private val detailViewModel by inject<DetailViewModel>()

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        (requireActivity() as ShoppingActivity).supportActionBar?.title = "Detail Product"

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        Log.d(TAG, "onViewCreated: ${args.product.id}")

        observeViewModel()
        initListener()
    }

    private fun initListener() {
        btn_add_cart.setOnClickListener {
            cartViewModel.getCartById(args.product.id).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Toast.makeText(requireContext(), "Product is Exist", Toast.LENGTH_SHORT).show()
                } else {
                    val cart = Cart()
                    cart.productId = args.product.id
                    cart.productName = args.product.product
                    cart.price = args.product.price.toDouble()
                    cart.imageUrl = args.product.image
                    cart.currentDate = getCurrentDate()
                    cartViewModel.insertCart(cart)
                    this.dialogCustomCart(R.id.action_detailFragment_to_cartFragment, 0)
                }
            })
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
        detailViewModel.getDetailProduct(args.product.id.toString())
        detailViewModel.detailProduct.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.data?.let { detail ->
                        layout_progressBar.hideLoading(requireActivity())
                        linear_detail.showView()
                        setDetailData(detail)
                    }
                }
                Status.ERROR -> {
                    layout_progressBar.hideLoading(requireActivity())
                    linear_detail.showView()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    layout_progressBar.showLoading(requireActivity())
                    linear_detail.hideView()
                }
            }
        })
    }

    private fun setDetailData(detail: Detail.Data) {

        tv_name.text = detail.product
        tv_price.text = Utils.rupiah(detail.price)
        if (detail.description.isNullOrEmpty()) {
            tv_description.text = getString(R.string.lorem_ipsumm)
        } else {
            tv_description.text = detail.description
        }

        val imageList = ArrayList<String>()
        val list = ArrayList<String>()

        for (img in detail.images) {
            list.add(img.image)
        }

        imageList.addAll(list)

        val sliderView = DefaultSliderView(requireContext())
        for (i in 0 until imageList.size) {
            sliderView.setProgressBarVisible(false)
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