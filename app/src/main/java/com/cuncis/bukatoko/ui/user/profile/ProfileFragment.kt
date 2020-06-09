package com.cuncis.bukatoko.ui.user.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.ui.user.UserViewModel
import com.cuncis.bukatoko.util.Constants.TAG
import com.cuncis.bukatoko.util.Utils.Companion.hideLoading
import com.cuncis.bukatoko.util.Utils.Companion.hideView
import com.cuncis.bukatoko.util.Utils.Companion.showLoading
import com.cuncis.bukatoko.util.Utils.Companion.showView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.btn_update

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var userViewModel: UserViewModel

    private var edit = false
    private var name: String? = null
    private var email: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        
        view.apply {
            observeViewModel()
            initListener()
        }
    }

    private fun initListener() {
        val userData = ShoppingPref.getUserData(requireContext())
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
                        userViewModel.updateUser(userData.userId,
                            et_name.text.toString(),
                            et_email.text.toString().trim(),
                            et_new_password.text.toString())
                            .observe(viewLifecycleOwner, Observer {  users ->
                                ShoppingPref.setUsername(requireContext(), users.data.id, users.data.name,
                                    users.data.email, et_new_password.text.toString())
                                tv_name.text = users.data.name
                                tv_email.text = users.data.email

                                et_name.setText(users.data.name)
                                et_email.setText(users.data.email)
                                et_current_password.setText("")
                                et_new_password.setText("")
                                et_new_re_password.setText("")
                                hideEdit()
                                Toast.makeText(requireContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show()
                            })
                    }
                }
            }
        }
        btn_cancel.setOnClickListener {
            hideEdit()
        }
    }

    private fun observeViewModel() {
        userViewModel.onLoading().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                layout_progressBar.showLoading(requireActivity())
            } else {
                layout_progressBar.hideLoading(requireActivity())
            }
        })
        userViewModel.getMessage().observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
        })
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