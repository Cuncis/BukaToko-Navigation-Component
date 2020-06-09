package com.cuncis.bukatoko.util

import android.view.Menu
import com.cuncis.bukatoko.R

class DrawerState {
    companion object {

        fun isLoggedIn(menu: Menu) {
            menu.findItem(R.id.nav_transaction).isVisible = true
            menu.findItem(R.id.nav_notif).isVisible = true
            menu.findItem(R.id.nav_profil).isVisible = true
            menu.findItem(R.id.nav_logout).isVisible = true

            menu.findItem(R.id.nav_login).isVisible = false
        }

        fun isLoggedOut(menu: Menu) {
            menu.findItem(R.id.nav_transaction).isVisible = false
            menu.findItem(R.id.nav_notif).isVisible = false
            menu.findItem(R.id.nav_profil).isVisible = false
            menu.findItem(R.id.nav_logout).isVisible = false

            menu.findItem(R.id.nav_login).isVisible = true
        }

    }
}