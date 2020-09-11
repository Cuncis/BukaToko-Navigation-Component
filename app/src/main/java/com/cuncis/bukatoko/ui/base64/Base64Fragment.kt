package com.cuncis.bukatoko.ui.base64

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.ui.ShoppingActivity
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Status
import kotlinx.android.synthetic.main.fragment_base64.*
import org.koin.android.ext.android.inject


class Base64Fragment : Fragment(R.layout.fragment_base64) {

    private val viewModel by inject<Base64ViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (requireActivity() as ShoppingActivity).supportActionBar?.title = "Base64 Test"

        val adapter = Base64Adapter()
        rv_base64.adapter = adapter

        viewModel.getBase64Data()
        viewModel.data.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it?.data?.data?.let { list ->
                        Log.d(TAG, "onViewCreated: ${list.size}")
                        adapter.submitList(list)
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Log.d(TAG, "onViewCreated: ${it.message}")
                }
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

}