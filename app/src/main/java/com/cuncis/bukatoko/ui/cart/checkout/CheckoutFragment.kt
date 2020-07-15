package com.cuncis.bukatoko.ui.cart.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.City
import com.cuncis.bukatoko.databinding.FragmentCheckoutBinding
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Status
import org.koin.android.ext.android.inject


class CheckoutFragment : Fragment() {

    private val checkoutViewModel by inject<CheckoutViewModel>()
    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        initListener()
    }

    private fun initListener() {
        binding.etOrigin.setOnClickListener {
            checkoutViewModel.getCities()
        }
    }

    private fun dialogCities(results: List<City.Response.Results.Data>) {
        val list = arrayListOf<String>()
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
        builder.setTitle("Select one city: ")

        for (i in results.indices) {
            list.add(results[i].cityName)
        }

        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_singlechoice, list)
        builder.setAdapter(arrayAdapter) { dialog, which ->
            binding.etOrigin.setText(list[which])
            dialog.dismiss()
        }
        builder.show()

    }

    private fun observeViewModel() {
        checkoutViewModel.cities.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.result?.let { data ->
                        dialogCities(data.results)
                    }
                }
                Status.ERROR -> {
                    Log.d(TAG, "observeViewModel: ${it.message}")
                }
                Status.LOADING -> {

                }
            }
        })
    }
}