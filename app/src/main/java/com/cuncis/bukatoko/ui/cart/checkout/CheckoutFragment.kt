package com.cuncis.bukatoko.ui.cart.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.data.model.Cart
import com.cuncis.bukatoko.data.model.City
import com.cuncis.bukatoko.data.model.TransactionData
import com.cuncis.bukatoko.databinding.FragmentCheckoutBinding
import com.cuncis.bukatoko.ui.cart.CartViewModel
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.RupiahHelper
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.dialog_custom.view.*
import org.koin.android.ext.android.inject
import kotlin.collections.ArrayList


class CheckoutFragment : Fragment() {

    private val checkoutViewModel by inject<CheckoutViewModel>()
    private val cartViewModel by inject<CartViewModel>()
    private lateinit var binding: FragmentCheckoutBinding

    private var ongkirValue: Int = 0
    private val details = arrayListOf<TransactionData.Data.Details>()

    private val args: CheckoutFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: ${ShoppingPref.getUserId(requireContext())}")

        observeViewModel()

        initListener()
    }

    private fun initListener() {
        binding.etOrigin.setOnClickListener {
            checkoutViewModel.getCities()
        }
        binding.btnSave.setOnClickListener {
            if (binding.etOrigin.length() > 0 && binding.etAddress.length() > 0) {
                binding.linearSave.hideView()

                setTransactionData()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please complete the field address!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.tvDismiss.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutFragment_to_cartFragment)
        }
        binding.btnTrans.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutFragment_to_transactionUploadFragment)
        }
        binding.tvCancel.setOnClickListener {
            Toast.makeText(requireContext(), "Go To Transaction", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_checkoutFragment_to_nav_transaction)
        }
    }

    private fun setTransactionData() {
        val data = TransactionData.Data()
        data.user_id = args.userId
        data.destination = "${binding.etOrigin.text} - ${binding.etAddress.text}"
        data.ongkir = ongkirValue
        data.grandtotal = args.grandTotal.toInt()
        data.detail = details
        checkoutViewModel.postTransaction(data)
    }

    private fun observeViewModel() {
        checkoutViewModel.cities.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.result?.let { data ->
                        dialogCities(data.results)
                    }
                }
                Status.ERROR -> { Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show() }
                Status.LOADING -> { }
            }
        })
        checkoutViewModel.cost.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val listService = arrayListOf<String>()
                    val listValue = arrayListOf<Int>()
                    it.data?.rajaongkir?.results?.let { results ->
                        for (i in results.indices) {
                            val costs = results[i].costs
                            for (j in costs.indices) {
                                val costItem = costs[j].cost
                                for (k in costItem.indices) {
                                    listService.add("Rp ${RupiahHelper.rupiah(costItem[k].value)} (JNE ${costs[j].service})")
                                    listValue.add(costItem[k].value)
                                    setAdapter(listService, listValue)
                                }
                            }
                        }
                    }
                }
                Status.ERROR -> { Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show() }
                Status.LOADING -> { }
            }
        })
        checkoutViewModel.transaction.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.hideView()
                    it.data?.let {
                        binding.linearTrans.showView()
                        cartViewModel.deleteCart()
                        Toast.makeText(
                            requireContext(),
                            "Transaction created successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.hideView()
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.progressBar.showView()
                }
            }
        })
        cartViewModel.getAllCarts().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                Toast.makeText(requireContext(), "Cart is Empty!", Toast.LENGTH_SHORT).show()
            } else {
                val detailData = TransactionData.Data.Details()
                for (i in it.indices) {
                    detailData.product_id = it[i].productId
                    detailData.price = it[i].price.toInt()
                    detailData.qty = it[i].qty
                    detailData.total = it[i].total.toInt()
                    details.add(detailData)
                }
            }
        })
    }

    private fun setAdapter(listService: ArrayList<String>, listValue: ArrayList<Int>) {
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listService)
        binding.spService.adapter = arrayAdapter
        binding.spService.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ongkirValue = listValue[position]
                binding.tvOngkir.text = String.format(getString(R.string.tv_ongkir), RupiahHelper.rupiah(listValue[position]))
            }

        }
    }

    private fun dialogCities(results: List<City.Response.Results.Data>) {
        var list = arrayListOf<City.Response.Results.Data>()
        val cityName = arrayListOf<String>()
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_custom, null)
        builder.setView(view)

        for (i in results.indices) {
            list.add(results[i])
            cityName.add(results[i].cityName)
        }

        val listView = view.lv_list
        val searchView = view.sv_list

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, cityName)

        val dialog = builder.create()

        listView.adapter = arrayAdapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val tempList = arrayListOf<City.Response.Results.Data>()
                val text = newText?.toLowerCase().toString()
                for (data in results) {
                    if (data.cityName.toLowerCase().contains(text)) {
                        tempList.add(data)
                    }
                }
                arrayAdapter.filter.filter(text)

                list = tempList
                return true
            }
        })
        listView.setOnItemClickListener { _, _, position, _ ->
            checkoutViewModel.postCost("444", list[position].cityId, "1000", "jne")
            binding.etOrigin.setText(list[position].cityName.capitalize())
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}