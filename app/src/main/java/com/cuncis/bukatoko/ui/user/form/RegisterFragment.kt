package com.cuncis.bukatoko.ui.user.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.ui.ShoppingActivity
import com.cuncis.bukatoko.ui.user.UserViewModel
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideLoading
import com.cuncis.bukatoko.util.Utils.Companion.showLoading
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.ext.android.inject


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val userViewModel by inject<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ShoppingActivity).title = "Register"

        observeViewModel(view)

        initListener()
    }

    private fun observeViewModel(view: View) {
        userViewModel.userData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.data?.let {
                        view.layout_progressBar.hideLoading(requireActivity())
                        Toast.makeText(requireContext(), "Register Successfully", Toast.LENGTH_SHORT).show()
                        when (findNavController().currentDestination?.id) {
                            R.id.registerFragment -> {
                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    view.layout_progressBar.hideLoading(requireActivity())
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    view.layout_progressBar.showLoading(requireActivity())
                }
            }
        })
    }

    private fun initListener() {
        btn_register_now.setOnClickListener {
            if (et_name.text.toString().isEmpty() || et_email.text.toString().isEmpty() || 
                et_password.text.toString().isEmpty() || et_re_password.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Fill the Field Properly!", Toast.LENGTH_SHORT).show()
            } else {
                if (et_password.text.toString() == et_re_password.text.toString()) {
                    userViewModel.register(
                        et_name.text.toString().trim(),
                        et_email.text.toString().trim(),
                        et_password.text.toString())
                } else {
                    Toast.makeText(requireContext(), "Password not matches!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}