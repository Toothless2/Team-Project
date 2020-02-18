package com.group7.unveil

import android.os.Bundle

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

import android.view.Gravity
import android.view.View

import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

import com.google.android.material.navigation.NavigationView

import androidx.drawerlayout.widget.DrawerLayout

import androidx.appcompat.app.AppCompatActivity


import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class Settings : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val mAppBarConfiguration: AppBarConfiguration? = null
    internal lateinit var navigationView: NavigationView
    internal lateinit var drawer: DrawerLayout
    internal lateinit var switch_id: SwitchCompat
    internal lateinit var seekbar: SeekBar
    internal lateinit var dark: ImageButton
    internal lateinit var light: ImageButton
    internal lateinit var signOut: Button
    internal lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)


        val set = findViewById<FloatingActionButton>(R.id.set)
        set.setOnClickListener { drawer.openDrawer(Gravity.RIGHT) }

        drawer = findViewById(R.id.drawer_layout)

        drawer.setOnClickListener { drawer.openDrawer(Gravity.RIGHT) }
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        val menu = navigationView.menu
        val menuItem = menu.findItem(R.id.nav_share)
        val actionView = MenuItemCompat.getActionView(menuItem)

        dark = actionView.findViewById(R.id.imagebutt)
        light = actionView.findViewById(R.id.imagebutt2)


        dark.setOnClickListener {
            navigationView.setBackgroundColor(resources.getColor(R.color.black))

        }

        light.setOnClickListener {
            drawer.setBackgroundColor(resources.getColor(R.color.white))
        }


        signOut = findViewById(R.id.signoutbutton)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        signOut.setOnClickListener { v ->
            when (v.id) {
                R.id.signoutbutton -> signOut()
            }
        }

    }


    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            drawer.openDrawer(GravityCompat.END)
            return true
        } else
            return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, mAppBarConfiguration!!) || super.onSupportNavigateUp()
    }


    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {

                Toast.makeText(this@Settings, "Successfully signed out", Toast.LENGTH_LONG).show()

                finish()
            }
    }

}