package com.cuncis.bukatoko.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Cart
import com.cuncis.bukatoko.databinding.ItemCartBinding
import com.cuncis.bukatoko.util.RupiahHelper
import com.cuncis.bukatoko.util.Utils.Companion.setImageFromUrl
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartList = ArrayList<Cart>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = CartViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_cart,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.binding.cart = cartList[position]
        holder.binding.btnDelete.setOnClickListener {
            Toast.makeText(holder.itemView.context, cartList[position].productName, Toast.LENGTH_SHORT).show()
        }
        holder.binding.spinQty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val qty = parent?.getItemAtPosition(pos).toString().toInt()
                holder.binding.tvTotal.text = RupiahHelper.rupiah((qty * cartList[position].price))
            }

        }
    }

    fun setCartList(newCartList: List<Cart>) {
        cartList.clear()
        cartList.addAll(newCartList)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(val binding: ItemCartBinding)
        : RecyclerView.ViewHolder(binding.root)
}