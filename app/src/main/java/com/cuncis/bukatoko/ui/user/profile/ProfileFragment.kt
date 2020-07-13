package com.cuncis.bukatoko.ui.user.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.ui.user.UserViewModel
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideLoading
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showLoading
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val userViewModel by inject<UserViewModel>()

    private var edit = false
    private var name: String? = null
    private var email: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        view.apply {
            initListener()
        }
    }

    private fun observeViewModel() {
        userViewModel.userData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.data?.let { user ->
                        layout_progressBar.hideLoading(requireActivity())
                        ShoppingPref.setUsername(requireContext(), user.id, user.name,
                            user.email, et_new_password.text.toString())
                        tv_name.text = user.name
                        tv_email.text = user.email

                        et_name.setText(user.name)
                        et_email.setText(user.email)
                        et_current_password.setText("")
                        et_new_password.setText("")
                        et_new_re_password.setText("")
                        hideEdit()
                        Toast.makeText(requireContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                }
                Status.ERROR -> {
                    layout_progressBar.hideLoading(requireActivity())
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    layout_progressBar.showLoading(requireActivity())
                }
            }
        })
    }

    private fun initListener() {
        val userData = ShoppingPref.getUserData(requireContext())
        Log.d("_log", "initListener: $userData")
        name = userData.name
        email = userData.email

        tv_name.text = name
        tv_email.text = email

        btn_update.setOnClickListener {
            et_name.setText(name)
            et_email.setText(email)
            showEdit()
        }
        btn_save.setOnClickListener {
            if (et_name.text.isEmpty() || et_email.text.isEmpty() ||
                et_new_password.text.isEmpty() || et_new_re_password.text.isEmpty()) {
                Toast.makeText(requireContext(), "Fill the field properly!", Toast.LENGTH_SHORT).show()
            } else {
                when {
                    userData.password != et_current_password.text.toString() -> {
                        Toast.makeText(
                            requireContext(),
                            "Wrong Current Password!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    et_new_password.text.toString() != et_new_re_password.text.toString() -> {
                        Toast.makeText(
                            requireContext(),
                            "New Password doesn't matches!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        userViewModel.update(userData.userId,
                            et_name.text.toString(),
                            et_email.text.toString().trim(),
                            et_new_password.text.toString())
                    }
                }
            }
        }
        btn_cancel.setOnClickListener {
            hideEdit()
        }
    }

    private fun showEdit() {
        linear_data.hideView()
        linear_edit.showView()
        edit = false
    }

    private fun hideEdit() {
        linear_data.showView()
        linear_edit.hideView()
        edit = true
    }
}