package com.cuncis.bukatoko.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.cuncis.bukatoko.R

class ContainerTransactionFragment : Fragment(R.layout.fragment_container_transaction) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStartDestinationChanged()
    }

    private fun onStartDestinationChanged() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_transaction)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_transaction)

        when (arguments?.getString("to")) {
            "tab_transaction" -> {
                navGraph.startDestination = R.id.tabTransactionFragment
                navController.graph = navGraph
            }
            "upload" -> {
                navGraph.startDestination = R.id.transactionUploadFragment2
                val bundle = bundleOf("userId" to arguments?.getString("userId"))
                navController.setGraph(navGraph, bundle)
            }
        }
    }

}