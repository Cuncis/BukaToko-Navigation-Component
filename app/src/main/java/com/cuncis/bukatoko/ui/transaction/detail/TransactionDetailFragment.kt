package com.cuncis.bukatoko.ui.transaction.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.databinding.FragmentTransactionDetailBinding
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showView
import org.koin.android.ext.android.inject

class TransactionDetailFragment : Fragment() {

    private lateinit var binding: FragmentTransactionDetailBinding

    private val viewModel by inject<TransactionDetailViewModel>()

    private val args by navArgs<TransactionDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transaction_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TransactionDetailAdapter()
        binding.recyclerviewTransDetail.adapter = adapter

        binding.switchTagihan.setOnCheckedChangeListener { _, isChecked1 ->
            binding.swTagihan = isChecked1
        }
        binding.switchPengiriman.setOnCheckedChangeListener { _, isChecked2 ->
            binding.swPengiriman = isChecked2
        }

        viewModel.getTransactionPaid(args.transactionCode)
        viewModel.transactionDetail.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.hideView()
                    it.data?.data?.let { data ->
                        adapter.submitList(data.detailTransaction)
                        binding.detail = data
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.showView()
                }
                Status.ERROR -> {
                    binding.progressBar.hideView()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}