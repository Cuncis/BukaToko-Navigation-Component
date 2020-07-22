package com.cuncis.bukatoko.ui.transaction

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.databinding.FragmentTransactionUnpaidBinding
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.ErrorUtils
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.fragment_transaction.*
import org.koin.android.ext.android.inject

class TransactionUnpaidFragment : Fragment() {

    private val transactionViewModel by inject<TransactionViewModel>()

    private lateinit var binding: FragmentTransactionUnpaidBinding
    private lateinit var unpaidAdapter: TransactionAdapter

    private val navController: NavController? by lazy {
        Navigation.findNavController(requireActivity(), R.id.nested_nav_host_fragment_home)
    }

    private val mainHelpController: NavController? by lazy { view?.findNavController() }

//    private val navGraph: NavGraph? by lazy {
//        navController?.navInflater?.inflate(R.navigation)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transaction_unpaid,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        observeViewModel()
    }

    private fun initRecyclerView() {
        unpaidAdapter = TransactionAdapter()
        binding.rvUnpaid.adapter = unpaidAdapter
        unpaidAdapter.onUploadClick = {
            if (navController?.currentDestination?.id == R.id.transactionUnpaidFragment) {
                navController?.navigate(R.id.action_transactionUnpaidFragment_to_transactionUploadFragment)
            }
        }
    }

    private fun observeViewModel() {
        transactionViewModel.getTransactionUnpaid(ShoppingPref.getUserId(requireContext()).toString())
        transactionViewModel.transactionUnpaidList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.hideView()
                    it.data?.data?.let { transactionList ->
                        when (it.data.status.code) {
                            200 -> {
                                unpaidAdapter.submitList(transactionList)
                            }
                            404 -> {
                                binding.tvNotFound.showView()
                                binding.tvNotFound.text = it.message
                            }
                            else -> {
                                Toast.makeText(requireContext(), it.data.status.description, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.hideView()
                    binding.tvNotFound.showView()
                    binding.tvNotFound.text = it.message
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.progressBar.showView()
                }
            }
        })
    }
}