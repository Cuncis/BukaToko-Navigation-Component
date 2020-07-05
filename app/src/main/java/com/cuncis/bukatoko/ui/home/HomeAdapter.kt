package com.cuncis.bukatoko.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.databinding.ItemProductBinding
import com.cuncis.bukatoko.util.Utils
import com.cuncis.bukatoko.util.Utils.Companion.setImageFromUrl
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.item_product.view.*

class HomeAdapter(private val productClickListener: OnProductClickListener): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var productList: ArrayList<Product.Data> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = HomeViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.product = productList[position]
        holder.itemView.setOnClickListener {
            productClickListener.onItemClick(productList[position])
        }
    }

    fun setProductList(products: List<Product.Data>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(val binding: ItemProductBinding)
        : RecyclerView.ViewHolder(binding.root) {
    }

    interface OnProductClickListener {
        fun onItemClick(product: Product.Data)
    }
}
