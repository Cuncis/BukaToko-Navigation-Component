package com.cuncis.bukatoko.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.util.Constants
import com.cuncis.bukatoko.util.DrawerState

class ShoppingActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initNavigation()

        initDrawer()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)
        navGraph.startDestination = R.id.nav_home
        navController.graph = navGraph
    }

    private fun initDrawer() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_transaction,
            R.id.nav_notif,
            R.id.nav_profil
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        Log.d(Constants.TAG, "initDrawer: ${ShoppingPref.getUsername(this)}")

        if (ShoppingPref.getUsername(this) == "") {
            DrawerState.isLoggedOut(navView.menu)
        } else {
            DrawerState.isLoggedIn(navView.menu)
        }

        navView.menu.getItem(0).isChecked = false
        navView.menu.getItem(1).isChecked = false

        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            Toast.makeText(this@ShoppingActivity, "Logout Successfully", Toast.LENGTH_SHORT).show()
            ShoppingPref.clearPref(this)
            DrawerState.isLoggedOut(navView.menu)
            drawerLayout.closeDrawer(GravityCompat.START)
            initNavigation()
            return@setOnMenuItemClickListener true
        }
        navView.menu.findItem(R.id.nav_login).setOnMenuItemClickListener {
            navView.menu.getItem(1).isCheckable = false
            drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_nav_home_to_loginFragment
            )

            return@setOnMenuItemClickListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cart) {
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_nav_home_to_loginFragment
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        initDrawer()
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigateUp(appBarConfiguration)
    }

}