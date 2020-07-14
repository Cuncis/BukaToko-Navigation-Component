package com.cuncis.bukatoko.ui.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Cart
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.RupiahHelper
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.android.ext.android.inject


class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.OnItemSelectedListener {

    private lateinit var cartAdapter: CartAdapter

    private val cartViewModel by inject<CartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)

        cartAdapter = CartAdapter(this)
        rv_cart.adapter = cartAdapter

        cartViewModel.getAllCarts().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                tv_messageEmpty.showView()
            } else {
                cartAdapter.setCartList(it)
            }
        })
    }

    override fun onItemSelected(cartList: ArrayList<Cart>, position: Int, total: Double) {
        var priceTotal = 0.0
        cartList[position].total = total

        for (i in 0 until cartList.size) {
            priceTotal += cartList[i].total
        }

        tv_price_total.text = String.format(getString(R.string.total_cart), RupiahHelper.rupiah(priceTotal))
    }

}