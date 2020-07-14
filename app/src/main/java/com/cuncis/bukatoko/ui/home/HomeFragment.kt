package com.cuncis.bukatoko.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.databinding.FragmentHomeBinding
import com.cuncis.bukatoko.util.Constants.PRODUCT_DETAIL_EXTRA
import com.cuncis.bukatoko.util.Status
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.fragment_home),
    HomeAdapter.OnProductClickListener {

    private lateinit var homeAdapter: HomeAdapter

    private val homeViewModel by inject<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        observeViewModel()

        initListener()
    }

    private fun initListener() {
        binding.swipeProduct.setOnRefreshListener {
            binding.swipeProduct.isRefreshing = false

            homeViewModel.getAllProducts()
        }
    }

    private fun observeViewModel() {
        homeViewModel.getAllProducts()
        homeViewModel.dataProducts.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.data?.let { data ->
                        binding.swipeProduct.isRefreshing = false
                        homeAdapter.setProductList(data)
                    }
                }
                Status.ERROR -> {
                    binding.swipeProduct.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.swipeProduct.isRefreshing = true
                }
            }
        })
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter(this)
        binding.rvProduct.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    override fun onItemClick(product: Product.Data) {
        val action = HomeFragmentDirections.actionNavHomeToDetailFragment(product)
        findNavController().navigate(action)
//        val bundle = Bundle()
//        bundle.putParcelable(PRODUCT_DETAIL_EXTRA, product)
//        findNavController().navigate(R.id.action_nav_home_to_detailFragment, bundle)
    }
}