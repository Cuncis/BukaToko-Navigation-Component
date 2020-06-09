package com.cuncis.bukatoko.ui.user.form

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.ui.ShoppingActivity
import com.cuncis.bukatoko.ui.user.UserViewModel
import com.cuncis.bukatoko.util.Constants.TAG
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var userViewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ShoppingActivity).title = "Register"

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        userViewModel.getMessage().observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
        })

        initListener()
    }

    private fun initListener() {
        btn_register_now.setOnClickListener {
            if (et_name.text.toString().isEmpty() || et_email.text.toString().isEmpty() || 
                et_password.text.toString().isEmpty() || et_re_password.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Fill the Field Properly!", Toast.LENGTH_SHORT).show()
            } else {
                if (et_password.text.toString() == et_re_password.text.toString()) {
                    userViewModel.register(et_name.text.toString().trim(), et_email.text.toString().trim(), et_password.text.toString())
                        .observe(viewLifecycleOwner, Observer { users ->
                            Log.d(TAG, "initListener: User Data from Register: ${users.data}")
                        })
                } else {
                    Toast.makeText(requireContext(), "Password not matches!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}