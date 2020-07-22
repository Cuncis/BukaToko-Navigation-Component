package com.cuncis.bukatoko.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.util.Status
import kotlinx.android.synthetic.main.fragment_transaction.*
import org.koin.android.ext.android.inject

class TransactionFragment : Fragment(R.layout.fragment_transaction) {

//    private val navController: NavController? by lazy {
//        Navigation.findNavController(requireActivity(), R.id.nested_nav_host_fragment_home)
//    }
//    private val navGraph: NavGraph? by lazy {
//        navController?.navInflater?.inflate(R.navigation.transaction_graph)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

//        initGraph()
    }

//    private fun initGraph() {
//        navGraph?.startDestination = R.id.transactionHostFragment
//        navGraph?.let {
//            navController?.setGraph(it)
//        }
//    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}