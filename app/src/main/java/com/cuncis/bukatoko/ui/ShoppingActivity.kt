package com.cuncis.bukatoko.ui


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.cuncis.bukatoko.R
import com.cuncis.bukatoko.data.local.ShoppingPref
import com.cuncis.bukatoko.util.Constants
import com.cuncis.bukatoko.util.DrawerState
import kotlinx.android.synthetic.main.activity_shopping.*


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
            if (ShoppingPref.getUsername(this) == "") {
                dialogLoginAlert()
            } else {
                if (findNavController(R.id.nav_host_fragment).currentDestination?.id == R.id.nav_home) {
//                    val bundle = bundleOf("to" to "cart")
                    val bundle = Bundle()
                    bundle.putString("to", "cart")
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_detail, bundle)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dialogLoginAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("Alert")
        builder.setMessage("You are not login yet.")
        builder.setPositiveButton("Go To Login") { dialog, _ ->
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_nav_home_to_loginFragment
            )
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onSupportNavigateUp(): Boolean {
        initDrawer()
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        when (findNavController(R.id.nav_host_fragment).currentDestination?.id) {
            R.id.nav_profil -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    navController.navigate(R.id.action_nav_profil_to_nav_home)
                }
            }
            R.id.nav_transaction -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    navController.navigate(R.id.action_nav_transaction_to_nav_home)
                }
            }
            R.id.nav_notif -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    navController.navigate(R.id.action_nav_notif_to_nav_home)
                }
            }
            else -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

}