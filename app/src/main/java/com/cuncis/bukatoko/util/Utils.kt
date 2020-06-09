package com.cuncis.bukatoko.util

import android.content.Context.INPUT_METHOD_SERVICE
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.cuncis.bukatoko.util.Constants.TAG
import java.lang.Exception


class Utils {
    companion object {
        fun ImageView.setImageFromUrl(url: String) {
            val circularProgressDrawable = CircularProgressDrawable(this.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(this);
        }

        fun View.showView() {
            this.visibility = View.VISIBLE
        }

        fun View.hideView() {
            this.visibility = View.GONE
        }

        fun View.showLoading(act: FragmentActivity) {
            this.visibility = View.VISIBLE
            act.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            try {
                val imm: InputMethodManager = act.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(act.currentFocus?.windowToken, 0)
            } catch (e: Exception) {
                Log.d(TAG, "showLoading: Keyboard: ${e.localizedMessage}")
            }
        }

        fun View.hideLoading(act: FragmentActivity) {
            this.visibility = View.GONE
            act.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            try {
                val imm: InputMethodManager = act.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(act.currentFocus?.windowToken, 0)
            } catch (e: Exception) {
                Log.d(TAG, "showLoading: Keyboard: ${e.localizedMessage}")
            }
        }
    }
}