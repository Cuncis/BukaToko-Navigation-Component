package com.cuncis.bukatoko.ui.cart

import android.util.Log
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

class CartAdapter(val onItemSelectedListener: OnItemSelectedListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

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
            onItemSelectedListener.onItemDeleted(position)
        }
        holder.binding.spinQty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val qty = parent?.selectedItem.toString().toInt()
                val total = qty * cartList[position].price
                holder.binding.tvTotal.text = RupiahHelper.rupiah(total)
                onItemSelectedListener.onItemSelected(cartList, position, total)
            }
        }
    }

    fun setCartList(newCartList: List<Cart>) {
        cartList.clear()
        cartList.addAll(newCartList)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        cartList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, cartList.size)
    }

    fun clearCart() {
        cartList.clear()
        notifyDataSetChanged()
    }

    inner class CartViewHolder(val binding: ItemCartBinding)
        : RecyclerView.ViewHolder(binding.root)

    interface OnItemSelectedListener {
        fun onItemSelected(cartList: ArrayList<Cart>, position: Int, total: Double)
        fun onItemDeleted(position: Int)
    }
}