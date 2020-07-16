package com.cuncis.bukatoko.ui.user.form

import android.os.Bundle
import android.view.Menu
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
import com.cuncis.bukatoko.util.Status
import com.cuncis.bukatoko.util.Utils.Companion.hideLoading
import com.cuncis.bukatoko.util.Utils.Companion.isValidEmailId
import com.cuncis.bukatoko.util.Utils.Companion.showLoading
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.koin.android.ext.android.inject


class LoginFragment : Fragment(R.layout.fragment_login) {

    private val userViewModel by inject<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (requireActivity() as ShoppingActivity).title = "Login"


        observeViewModel(view)

        initListener()
    }

    private fun observeViewModel(view: View) {
        userViewModel.userData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.data?.let { user ->
                        view.layout_progressBar.hideLoading(requireActivity())
                        ShoppingPref.setUsername(
                            requireContext(),
                            user.id,
                            user.name,
                            user.email,
                            et_password.text.toString())
                        Toast.makeText(requireContext(), "Login Successfully as ${ShoppingPref.getUsername(requireContext())}", Toast.LENGTH_SHORT).show()
                        when (findNavController().currentDestination?.id) {
                            R.id.loginFragment -> {
                                findNavController().navigate(R.id.action_loginFragment_to_nav_home)
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
        btn_login.setOnClickListener {
            if (et_email.text.toString().trim().isEmpty() || et_password.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Fill the field please!", Toast.LENGTH_SHORT).show()
            } else {
                if (isValidEmailId(et_email.text.toString())) {
                    userViewModel.login(et_email.text.toString().trim(), et_password.text.toString())
                } else {
                    Toast.makeText(requireContext(), "Email not valid", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btn_register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}