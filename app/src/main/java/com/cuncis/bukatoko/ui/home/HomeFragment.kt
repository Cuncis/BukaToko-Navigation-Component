package com.cuncis.bukatoko.ui.home

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home),
    HomeAdapter.OnProductClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = HomeRepository()
        val factory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)

        initRecyclerView()

        observeViewModel()

        initListener()
    }

    private fun initListener() {
        swipe_product.setOnRefreshListener {
            swipe_product.isRefreshing = false

            homeViewModel.getAllProducts().observe(viewLifecycleOwner, Observer { productList ->
                homeAdapter.setProductList(productList)
            })
        }
    }

    private fun observeViewModel() {
        homeViewModel.getAllProducts().observe(viewLifecycleOwner, Observer { productList ->
            homeAdapter.setProductList(productList)
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