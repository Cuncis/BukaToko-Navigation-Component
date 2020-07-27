package com.cuncis.bukatoko.ui.transaction

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.databinding.FragmentTransactionBinding
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.TextHelper
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.fragment_transaction.*
import org.koin.android.ext.android.inject

class TransactionFragment : Fragment() {

    private val transactionViewModel by inject<TransactionViewModel>()
    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var binding: FragmentTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        TextHelper.underlineIndicator(binding.tvUnpaid)
        TextHelper.restoreIndicator(binding.tvPaid)
        binding.parentPaid.visibility = View.VISIBLE
        binding.parentUnpaid.visibility = View.GONE

        initRecyclerViewUnpaid()
        initRecyclerViewPaid()

        observeViewModel()
        initListener()
    }

    private fun initListener() {
        binding.swipeTransaction.setOnRefreshListener {
            initRecyclerViewUnpaid()
            initRecyclerViewPaid()
            binding.swipeTransaction.isRefreshing = false
        }
        binding.tvUnpaid.setOnClickListener {
            binding.parentPaid.visibility = View.VISIBLE
            binding.parentUnpaid.visibility = View.GONE
            TextHelper.underlineIndicator(binding.tvUnpaid)
            TextHelper.restoreIndicator(binding.tvPaid)
        }
        binding.tvPaid.setOnClickListener {
            binding.parentPaid.visibility = View.GONE
            binding.parentUnpaid.visibility = View.VISIBLE
            TextHelper.underlineIndicator(binding.tvPaid)
            TextHelper.restoreIndicator(binding.tvUnpaid)
        }
    }


    private fun initRecyclerViewUnpaid() {
        transactionAdapter = TransactionAdapter()
        binding.rvUnpaid.adapter = transactionAdapter
        transactionAdapter.onUploadClick = { code ->
            val action = TransactionFragmentDirections.actionNavTransactionToTransactionUploadFragment(code.toString())
            findNavController().navigate(action)
        }
    }

    private fun initRecyclerViewPaid() {
        transactionAdapter = TransactionAdapter()
        binding.rvPaid.adapter = transactionAdapter
        transactionAdapter.onUploadClick = { code ->
            val action = TransactionFragmentDirections.actionNavTransactionToTransactionUploadFragment(code.toString())
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        transactionViewModel.getTransactionUnpaid(ShoppingPref.getUserId(requireContext()).toString())
        transactionViewModel.getTransactionPaid(ShoppingPref.getUserId(requireContext()).toString())

        transactionViewModel.transactionUnpaidList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.hideView()
                    it.data?.data?.let { transactionList ->
                        transactionAdapter.submitList(transactionList)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.hideView()
                    binding.tvNotFoundUnpaid.text = it.message
                }
                Status.LOADING -> {
                    binding.progressBar.showView()
                }
            }
        })
        transactionViewModel.transactionPaidList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.hideView()
                    it.data?.data?.let { transactionList ->
                        transactionAdapter.submitList(transactionList)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.hideView()
                    binding.tvNotFoundPaid.text = it.message
                }
                Status.LOADING -> {
                    binding.progressBar.showView()
                }
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}