package com.cuncis.bukatoko.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.util.Constants.PRODUCT_DETAIL_EXTRA
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Status
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.fragment_home),
    HomeAdapter.OnProductClickListener {

    private lateinit var homeAdapter: HomeAdapter

    private val homeViewModel by inject<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        observeViewModel()

        initListener()
    }

    private fun initListener() {
        swipe_product.setOnRefreshListener {
            swipe_product.isRefreshing = false

            homeViewModel.getAllProducts()
        }
    }

    private fun observeViewModel() {
        homeViewModel.getAllProducts()
        homeViewModel.dataProducts.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.data?.let { data ->
                        swipe_product.isRefreshing = false
                        homeAdapter.setProductList(data)
                    }
                }
                Status.ERROR -> {
                    swipe_product.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    swipe_product.isRefreshing = true
                }
            }
        })
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter(this)
        rv_product.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    override fun onItemClick(product: Product) {
        val bundle = Bundle()
        bundle.putParcelable(PRODUCT_DETAIL_EXTRA, product)
        findNavController().navigate(R.id.action_nav_home_to_detailFragment, bundle)
    }
}