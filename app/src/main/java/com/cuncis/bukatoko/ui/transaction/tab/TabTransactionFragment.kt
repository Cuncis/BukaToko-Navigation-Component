package com.cuncis.bukatoko.ui.transaction.tab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.databinding.FragmentTabTransactionBinding
import com.cuncis.bukatoko.ui.transaction.detail.TransactionDetailFragment
import com.cuncis.bukatoko.util.Constants.TAB_KEY
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.fragment_tab_transaction.*
import org.koin.android.ext.android.inject


class TabTransactionFragment : Fragment() {

    private val transactionViewModel by inject<TransactionViewModel>()
    private lateinit var unpaidAdapter: UnpaidAdapter
    private lateinit var paidAdapter: PaidAdapter

    private lateinit var binding: FragmentTabTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tab_transaction,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()

        initUnpaid()

        initPaid()

        observeViewModel()

        initListener()
    }

    private fun initListener() {
        unpaidAdapter.setListener { code ->
            val directions = TransactionFragmentDirections.actionNavTransactionToTransactionDetailFragment(code.toString())
            findNavController().navigate(directions)
        }
        paidAdapter.setListener { code ->
            val directions = TransactionFragmentDirections.actionNavTransactionToTransactionDetailFragment(code.toString())
            findNavController().navigate(directions)
        }
    }

    private fun observeViewModel() {
        transactionViewModel.getTransactionUnpaid(
            ShoppingPref.getUserId(requireContext()).toString()
        )
        transactionViewModel.getTransactionPaid(
            ShoppingPref.getUserId(requireContext()).toString()
        )

        transactionViewModel.transactionUnpaidList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.hideView()
                    it.data?.data?.let { transactionList ->
                        unpaidAdapter.submitUnpaidList(transactionList)
                        Log.d(TAG, "observeViewModel: list data - $transactionList")
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
                        paidAdapter.submitPaidList(transactionList)
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

    private fun initUnpaid() {
        unpaidAdapter = UnpaidAdapter()
        binding.rvUnpaid.adapter = unpaidAdapter
    }

    private fun initPaid() {
        paidAdapter = PaidAdapter()
        binding.rvPaid.adapter = paidAdapter
    }

    private fun initArgs() {
        arguments?.takeIf { it.containsKey(TAB_KEY) }?.apply {
            val tabPosition = getInt(TAB_KEY)
            if (tabPosition == 1) {
                layout_unpaid.showView()
            } else {
                layout_paid.showView()
            }
        }
    }

}