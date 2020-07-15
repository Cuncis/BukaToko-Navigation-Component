package com.cuncis.bukatoko.ui.cart

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.model.Cart
import com.cuncis.bukatoko.databinding.FragmentCartBinding
import com.cuncis.bukatoko.util.RupiahHelper
import com.cuncis.bukatoko.util.Utils.Companion.showView
import org.koin.android.ext.android.inject


class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.OnItemSelectedListener {

    private lateinit var cartAdapter: CartAdapter
    private var carts = arrayListOf<Cart>()

    private val cartViewModel by inject<CartViewModel>()

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        cartAdapter = CartAdapter(this)
        binding.rvCart.adapter = cartAdapter

        cartViewModel.getAllCarts().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.tvMessageEmpty.showView()
            } else {
                carts.addAll(it)
                cartAdapter.setCartList(it)
            }
        })

        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }
    }

    private fun dialogAlert() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setCancelable(true)
        builder.setTitle("Do you want to delete all cart?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            cartViewModel.deleteCart()
            cartAdapter.clearCart()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onItemSelected(cartList: ArrayList<Cart>, position: Int, total: Double) {
        var priceTotal = 0.0
        cartList[position].total = total

        for (i in 0 until cartList.size) {
            priceTotal += cartList[i].total
        }

        binding.tvPriceTotal.text = String.format(getString(R.string.total_cart), RupiahHelper.rupiah(priceTotal))
    }

    override fun onItemDeleted(position: Int) {
        cartAdapter.removeItem(position)
        cartViewModel.deleteCartById(carts[position].productId)
        findNavController().navigate(R.id.action_cartFragment_self)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        menu.findItem(R.id.action_deleteAll).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_deleteAll) {
            if (carts.isEmpty()) {
                Toast.makeText(requireContext(), "Cart is Empty", Toast.LENGTH_SHORT).show()
            } else {
                dialogAlert()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}