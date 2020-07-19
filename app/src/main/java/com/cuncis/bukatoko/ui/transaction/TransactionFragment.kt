package com.cuncis.bukatoko.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.util.Status
import kotlinx.android.synthetic.main.fragment_transaction.*
import org.koin.android.ext.android.inject

class TransactionFragment : Fragment(R.layout.fragment_transaction) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        vp_transaction.adapter = TransactionPagerAdapter(childFragmentManager)
        tl_transaction.setupWithViewPager(vp_transaction)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}