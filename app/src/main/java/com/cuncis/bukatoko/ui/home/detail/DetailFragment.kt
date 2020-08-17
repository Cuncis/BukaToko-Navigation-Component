package com.cuncis.bukatoko.ui.home.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Cart
import com.cuncis.bukatoko.ui.cart.CartViewModel
import com.cuncis.bukatoko.data.model.Detail
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.databinding.FragmentDetailBinding
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
import org.koin.android.ext.android.inject


class DetailFragment : Fragment(), View.OnClickListener {

    private val detailViewModel by inject<DetailViewModel>()
    private val cartViewModel by inject<CartViewModel>()

    private lateinit var binding: FragmentDetailBinding
    private val detail: Product.Data by lazy { arguments?.getParcelable<Product.Data>("product") as Product.Data }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (requireActivity() as ShoppingActivity).supportActionBar?.title = "Detail Product"
        binding.listener = this

        observeViewModel()
    }

    private fun observeViewModel() {
        detailViewModel.getDetailProduct(detail.id.toString())
        detailViewModel.detailProduct.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.data?.let { detail ->
                        binding.layoutProgressBar.hideLoading(requireActivity())
                        binding.linearDetail.showView()
                        binding.detail = detail
                    }
                }
                Status.ERROR -> {
                    binding.layoutProgressBar.hideLoading(requireActivity())
                    binding.linearDetail.showView()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.layoutProgressBar.showLoading(requireActivity())
                    binding.linearDetail.hideView()
                }
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_add_cart) {
            cartViewModel.getCartById(detail.id).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Toast.makeText(requireContext(), "Product is Exist", Toast.LENGTH_SHORT).show()
                } else {
                    val cart = Cart()
                    cart.productId = detail.id
                    cart.productName = detail.product
                    cart.price = detail.price.toDouble()
                    cart.imageUrl = detail.image
                    cart.currentDate = getCurrentDate()
                    cartViewModel.insertCart(cart)
//                    this.dialogCustomCart(R.id.action_detailFragment_to_cartFragment)
                }
            })
        } else if (v?.id == R.id.btn_checkout) {
            Toast.makeText(requireContext(), "Checkout", Toast.LENGTH_SHORT).show()
        }
    }

}