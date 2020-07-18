package com.cuncis.bukatoko.ui.cart.checkout

import android.os.Bundle
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
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.City
import com.cuncis.bukatoko.databinding.FragmentCheckoutBinding
import com.cuncis.bukatoko.util.RupiahHelper
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.dialog_custom.view.*
import org.koin.android.ext.android.inject
import kotlin.collections.ArrayList


class CheckoutFragment : Fragment() {

    private val checkoutViewModel by inject<CheckoutViewModel>()
    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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
        binding.btnSave.setOnClickListener {
            if (binding.etOrigin.length() > 0 && binding.etAddress.length() > 0) {
                binding.linearTrans.showView()
                binding.linearSave.hideView()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please complete the field address!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
    }

    private fun setAdapter(
        listService: ArrayList<String>,
        listValue: ArrayList<Int>
    ) {
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

    private fun dialogCost() {
        var list = arrayListOf<String>()
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_custom, null)
        builder.setView(view)

//        for (i in results.indices) {
//            list.add(results[i].cityName)
//        }

        val listView = view.lv_list
        val searchView = view.sv_list
        searchView.hideView()
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)

        val dialog = builder.create()

        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            checkoutViewModel.postCost("444", list[position].capitalize(), "1000", "jne")
            binding.etOrigin.setText(list[position].capitalize())
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}