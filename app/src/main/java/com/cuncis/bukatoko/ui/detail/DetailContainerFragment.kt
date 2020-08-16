package com.cuncis.bukatoko.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.cuncis.bukatoko.R

class DetailContainerFragment : Fragment(R.layout.fragment_detail_container) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStartDestinationChanged()

    }

    private fun onStartDestinationChanged() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_transaction)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_transaction)

        when (arguments?.getString("to")) {
            "detail" -> {
                navGraph.startDestination = R.id.detailFragment2
                val bundle = bundleOf("product" to arguments?.getString("product"))
                navController.setGraph(navGraph, bundle)
            }
            "cart" -> {
                navGraph.startDestination = R.id.cartFragment2
                navController.setGraph(navGraph)
            }
            "checkout" -> {
                navGraph.startDestination = R.id.checkoutFragment2
                navController.setGraph(navGraph)
            }
        }
    }


}