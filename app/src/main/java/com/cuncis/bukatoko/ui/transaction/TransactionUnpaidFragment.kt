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
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.databinding.FragmentTransactionUnpaidBinding
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showView
import org.koin.android.ext.android.inject

class TransactionUnpaidFragment : Fragment() {

    private val transactionViewModel by inject<TransactionViewModel>()

    private lateinit var binding: FragmentTransactionUnpaidBinding
    private lateinit var unpaidAdapter: TransactionAdapter

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
            Toast.makeText(requireContext(), "Upload Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        transactionViewModel.getTransactionUnpaid(ShoppingPref.getUserId(requireContext()).toString())
        transactionViewModel.transactionUnpaidList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.hideView()
                    it.data?.data?.let { transactionList ->
                        unpaidAdapter.submitList(transactionList)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.hideView()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.progressBar.showView()
                }
            }
        })
    }
}