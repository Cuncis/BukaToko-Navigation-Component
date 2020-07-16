package com.cuncis.bukatoko.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.ui.cart.CartViewModel
import kotlinx.android.synthetic.main.dialog_cart.view.*
import kotlinx.android.synthetic.main.dialog_custom.view.*

object Dialogs {
    fun Fragment.dialogSample(message: String, resId: Int) {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setCancelable(true)
        builder.setTitle(message)
        builder.setPositiveButton("Yes") { dialog, _ ->
            this.findNavController().navigate(resId)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun Fragment.dialogCustomCart(resId1: Int) {
        val builder = AlertDialog.Builder(this.requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_cart, null)
        builder.setView(view)
        val dialog = builder.create()
        view.apply {
            btn_cart.setOnClickListener {
                findNavController().navigate(resId1)
                dialog.dismiss()
            }

            btn_pay.setOnClickListener {
                findNavController().navigate(resId1)
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}