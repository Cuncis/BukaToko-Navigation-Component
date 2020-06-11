package com.cuncis.bukatoko.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.persistence.Cart
import com.cuncis.bukatoko.util.Utils.Companion.setImageFromUrl
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter(val context: Context): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartList = ArrayList<Cart>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
        val cart = cartList[position]
        cart.qty = 1
        cart.total = cart.total
    }

    inner class CartViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.tv_name
        private val tvPrice: TextView = view.tv_price
        private val tvTotal: TextView = view.tv_total
        private val btnDelete: ImageButton = view.btn_delete
        private val spinQty: Spinner = view.spin_qty
        private val imgProduct: ImageView = view.img_product

        fun bind(cart: Cart) {
            tvName.text = cart.productName
            tvPrice.text = cart.price.toString()
            tvTotal.text = cart.total.toString()
            imgProduct.setImageFromUrl(cart.imageUrl)
            val arrayList = ArrayList<String>()
            for (i in 0..100) {
                arrayList.add(i.toString())
            }
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayList)
            spinQty.adapter = adapter

            spinQty.setOnItemClickListener { parent, view, position, id ->

            }

            btnDelete.setOnClickListener {

            }
        }
    }


}