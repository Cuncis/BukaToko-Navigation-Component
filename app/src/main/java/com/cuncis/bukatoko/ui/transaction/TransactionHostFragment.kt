package com.cuncis.bukatoko.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import kotlinx.android.synthetic.main.fragment_transaction_host.*


class TransactionHostFragment : Fragment(R.layout.fragment_transaction_host) {

    private val navController: NavController? by lazy {
        Navigation.findNavController(requireActivity(), R.id.nested_nav_host_fragment_home)
    }
    private val navGraph: NavGraph? by lazy {
        navController?.navInflater?.inflate(R.navigation.transaction_graph)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGraph()

        vp_transaction.adapter = TransactionPagerAdapter(childFragmentManager)
        tl_transaction.setupWithViewPager(vp_transaction)
    }

    private fun initGraph() {
        navGraph?.startDestination = R.id.transactionUnpaidFragment
        navGraph?.let {
            navController?.setGraph(it)
        }
    }

}