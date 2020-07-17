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
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.City
import com.cuncis.bukatoko.databinding.FragmentCheckoutBinding
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Status
import kotlinx.android.synthetic.main.dialog_custom.view.*
import org.koin.android.ext.android.inject
import java.util.*


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
    }

    private fun dialogCities(results: List<City.Response.Results.Data>) {
        val list = arrayListOf<String>()
        val tempList = arrayListOf<String>()
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_custom, null)
        builder.setView(view)

        for (i in results.indices) {
            list.add(results[i].cityName)
        }

        val listView = view.lv_list
        val searchView = view.sv_list
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)

        val dialog = builder.create()

        listView.adapter = arrayAdapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText?.toLowerCase(Locale.getDefault())
                for (data in results) {
                    val name = data.cityName
                    if (name.contains(text!!)) {
                        tempList.add(name)
                    }
                }
                arrayAdapter.filter.filter(text)
                return true
            }
        })
        listView.setOnItemClickListener { _, _, position, _ ->
            binding.etOrigin.setText(tempList[position])
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun observeViewModel() {
        checkoutViewModel.cities.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.result?.let { data ->
                        dialogCities(data.results)
                    }
                }
                Status.ERROR -> { Log.d(TAG, "observeViewModel: ${it.message}") }
                Status.LOADING -> { }
            }
        })
//        checkoutViewModel.city.observe(viewLifecycleOwner, Observer {
//            binding.etOrigin.setText(it)
//        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}