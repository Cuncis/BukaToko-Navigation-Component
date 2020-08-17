package com.cuncis.bukatoko.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.cuncis.bukatoko.R

class DetailContainerFragment : Fragment(R.layout.fragment_detail_container) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStartDestinationChanged()
    }

    private fun onStartDestinationChanged() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_detail)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_detail)

        when (arguments?.getString("to")) {
            "cart" -> {
                navGraph.startDestination = R.id.cartFragment2
                navController.graph = navGraph
            }
            "detail" -> {
                navGraph.startDestination = R.id.detailFragment2
                val bundle = bundleOf("product" to arguments?.getParcelable("product"))
                navController.setGraph(navGraph, bundle)
            }
            "checkout" -> {
                navGraph.startDestination = R.id.checkoutFragment2
                navController.graph = navGraph
            }
        }
    }


}