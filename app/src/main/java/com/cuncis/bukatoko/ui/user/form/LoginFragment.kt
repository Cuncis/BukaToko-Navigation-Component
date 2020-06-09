package com.cuncis.bukatoko.ui.user.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.ui.ShoppingActivity
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.ui.user.UserViewModel
import com.cuncis.bukatoko.util.Utils.Companion.hideLoading
import com.cuncis.bukatoko.util.Utils.Companion.showLoading
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var userViewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ShoppingActivity).title = "Login"

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        observeViewModel(view)

        initListener()
    }

    private fun observeViewModel(view: View) {
        userViewModel.onLoading().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                view.layout_progressBar.showLoading(requireActivity())
            } else {
                view.layout_progressBar.hideLoading(requireActivity())
            }
        })
        userViewModel.getMessage().observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initListener() {
        btn_login.setOnClickListener {
            if (et_email.text.toString().trim().isEmpty() || et_password.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Fill the field please!", Toast.LENGTH_SHORT).show()
            } else {
                userViewModel.login(et_email.text.toString().trim(), et_password.text.toString()).observe(viewLifecycleOwner, Observer { users ->
                    ShoppingPref.setUsername(requireContext(), users.data.id, users.data.name, users.data.email, et_password.text.toString())
                    Toast.makeText(requireContext(), "Login Successfully as ${ShoppingPref.getUsername(requireContext())}", Toast.LENGTH_SHORT).show()
                    when (findNavController().currentDestination?.id) {
                        R.id.loginFragment -> {
                            findNavController().navigate(R.id.action_loginFragment_to_nav_home)
                        }
                    }
                })
            }
        }

        btn_register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}