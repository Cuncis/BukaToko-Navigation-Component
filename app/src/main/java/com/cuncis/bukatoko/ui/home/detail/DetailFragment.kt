package com.cuncis.bukatoko.ui.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.ui.ShoppingActivity
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.util.Constants


class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productData: Product = arguments?.getParcelable(Constants.PRODUCT_DETAIL_EXTRA)!!
        (requireActivity() as ShoppingActivity).supportActionBar?.title = productData.product
    }
}