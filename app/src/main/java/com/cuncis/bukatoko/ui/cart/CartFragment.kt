package com.cuncis.bukatoko.ui.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.persistence.CartViewModel
import com.cuncis.bukatoko.util.Constants.TAG


class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var cartViewModel: CartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        cartViewModel.getAllCarts().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onViewCreated: $it")
        })
    }

}