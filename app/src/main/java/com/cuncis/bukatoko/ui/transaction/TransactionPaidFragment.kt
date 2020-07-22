package com.cuncis.bukatoko.ui.transaction

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
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.databinding.FragmentTransactionPaidBinding
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.ErrorUtils
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.fragment_transaction.*
import org.koin.android.ext.android.inject


class TransactionPaidFragment : Fragment() {

    private val transactionViewModel by inject<TransactionViewModel>()

    private lateinit var binding: FragmentTransactionPaidBinding
    private lateinit var paidAdapter: TransactionAdapter

    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction_paid, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = childFragmentManager.findFragmentById(R.id.nested_nav_host_fragment_home)?.findNavController()

        initRecyclerView()

        observeViewModel()
    }

    private fun initRecyclerView() {
        paidAdapter = TransactionAdapter()
        binding.rvPaid.adapter = paidAdapter
        paidAdapter.onUploadClick = {
            findNavController().navigate(R.id.action_transactionPaidFragment_to_transactionUploadFragment)
        }
    }

    private fun observeViewModel() {
        transactionViewModel.getTransactionPaid(ShoppingPref.getUserId(requireContext()).toString())
        transactionViewModel.transactionPaidList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.hideView()
                    it.data?.data?.let { transactionList ->
                        when (it.data.status.code) {
                            200 -> {
                                paidAdapter.submitList(transactionList)
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
                }
                Status.LOADING -> {
                    binding.progressBar.showView()
                }
            }
        })
    }

}