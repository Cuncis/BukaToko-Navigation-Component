package com.cuncis.bukatoko.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Product
import com.cuncis.bukatoko.util.Utils
import com.cuncis.bukatoko.util.Utils.Companion.setImageFromUrl
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.item_product.view.*

class HomeAdapter(val productClickListener: OnProductClickListener): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var productList: ArrayList<Product.Data> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    fun setProductList(products: List<Product.Data>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var imgPoster: ImageView = view.img_product
        var tvPrice: TextView = view.tv_price
        var tvName: TextView = view.tv_name

        fun bind(product: Product.Data) {
            tvName.showView()
            tvPrice.showView()
            tvName.text = product.product
            tvPrice.text = Utils.rupiah(product.price)
            imgPoster.setImageFromUrl(product.image)
            itemView.setOnClickListener {
                 productClickListener.onItemClick(product)
            }
        }
    }

    interface OnProductClickListener {
        fun onItemClick(product: Product.Data)
    }
}
