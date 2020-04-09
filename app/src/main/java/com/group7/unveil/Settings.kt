package com.group7.unveil

import android.app.Activity
import android.os.Bundle
import android.view.*

import com.google.android.material.floatingactionbutton.FloatingActionButton

import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

import com.google.android.material.navigation.NavigationView

import androidx.drawerlayout.widget.DrawerLayout

import androidx.appcompat.app.AppCompatActivity


import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.group7.unveil.data.StepData
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkListener
import com.group7.unveil.events.StepListener
import kotlinx.android.synthetic.main.activity_user_page.*

class Settings : Fragment(), NavigationView.OnNavigationItemSelectedListener,
    StepListener,
    LandmarkListener {

     val mAppBarConfiguration: AppBarConfiguration? = null
     lateinit var navigationView: NavigationView
     lateinit var drawer: DrawerLayout
     lateinit var switch_id: SwitchCompat
    lateinit var switch_id2: SwitchCompat
     lateinit var seekbar: SeekBar
     lateinit var dark: ImageButton
     lateinit var light: ImageButton
     lateinit var signOut: Button
     lateinit var mGoogleSignInClient: GoogleSignInClient
    internal var language = arrayOf("English", "Polish", "German", "Bulgarian")
    internal var textSizes = arrayOf("Small", "Medium", "Big")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getActivity()?.let { Utils.onActivityCreateSetTheme(it) }
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.settings, container, false)
//        super.onCreate(savedInstanceState)
        EventBus.subscribeToLandmarkEvent(this)
        EventBus.subscribeToStepEvent(this)

        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.me)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawer = getView()!!.findViewById(R.id.drawer_layout)
        val set = view!!.findViewById<FloatingActionButton>(R.id.set)
        set.setOnClickListener { drawer.openDrawer(Gravity.RIGHT) }
        navigationView = getView()!!.findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val menu = navigationView.menu
        val menuColorBlind = menu.findItem(R.id.colorblind)
        val actionViewColorBlind = MenuItemCompat.getActionView(menuColorBlind)

        //colorblind mode switch
        switch_id = actionViewColorBlind.findViewById(R.id.switch_id)
        //switch_id.isChecked = true
        switch_id.setOnClickListener {

            if (switch_id.isChecked) {
                //code to turn on colorblind mode
            } else if (!switch_id.isChecked) {
                //code to turn off colorblind mode
            }
        }

        val menuDyslexic = menu.findItem(R.id.dyslex)
        val actionViewDyslexic= MenuItemCompat.getActionView(menuDyslexic)

        //dyslexic font switch
        switch_id2 = actionViewDyslexic.findViewById(R.id.switch_id2)
        //switch_id2.isChecked = true
        switch_id2.setOnClickListener {
            if (switch_id2.isChecked) {
                getActivity()?.let { it1 -> Utils.changeToTheme(it1, Utils.Dyslexic) }
            } else if (!switch_id2.isChecked) {
                getActivity()?.let { it1 -> Utils.changeToTheme(it1, Utils.LightTheme) }

            }
        }

        val menuDarkTheme = menu.findItem(R.id.darkbutton)
        val actionViewDarkTh = MenuItemCompat.getActionView(menuDarkTheme)

        //button to turn dark mode
        dark = actionViewDarkTh.findViewById(R.id.imagebutt)
        dark.setOnClickListener {
            getActivity()?.let { it1 -> Utils.changeToTheme(it1, Utils.DarkTheme) }

        }

        val menuLightTheme= menu.findItem(R.id.whitebutton)
        val actionViewLightTh = MenuItemCompat.getActionView(menuLightTheme)

        //button to turn light mode
        light = actionViewLightTh.findViewById(R.id.imagebutt2)
        light.setOnClickListener {
            getActivity()?.let { it1 -> Utils.changeToTheme(it1, Utils.LightTheme) }
        }

        //spinner for languages
        val spinner = navigationView.menu.findItem(R.id.lang).actionView as Spinner
        spinner.adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, language) }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                Toast.makeText(context, language[position], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


      //  spinner for text sizes
        val spinner2 = navigationView.menu.findItem(R.id.textsize).actionView as Spinner
        spinner2.adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, textSizes) }
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                when(position) {

                    1 -> getActivity()?.let { Utils.changeToTheme(it, Utils.Medium) }
                    2 -> getActivity()?.let { Utils.changeToTheme(it, Utils.Big) }
                }
//
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val signOutMenuItem = menu.findItem(R.id.signOutButton)
        val actionViewSignOut = MenuItemCompat.getActionView(signOutMenuItem)
        signOut = actionViewSignOut.findViewById(R.id.signoutbutton)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)


        signOut.setOnClickListener { v ->
            when (v.id) {
                R.id.signoutbutton -> signOut()
            }
        }


        // update the ui M. Rose
        stepEvent(StepData.steps)
        updateVisitedUI(StepData.locationsVisited)
    }

//     fun onBackPressed() {
//        val drawer = getView()!!.findViewById<DrawerLayout>(R.id.drawer_layout)
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START)
//        } else {
//            onBackPressed()
//        }
//    }

     override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.settings, menu)

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

        val drawer = getView()!!.findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

//     fun onSupportNavigateUp(): Boolean {
//        val navController = Navigation.findNavController(this.requireActivity(), R.id.nav_host_fragment)
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration!!) || onSupportNavigateUp()
//    }


    private fun signOut() {
        getActivity()?.let {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(it) {

                    Toast.makeText(context, "Successfully signed out", Toast.LENGTH_LONG).show()
                    activity?.finish()
                }
        }
    }

    /**
     * @author M. Rose
     */
    override fun stepEvent(steps: Int) {
        step_count1.text = steps.toString()
        distance_actual1.text = StepData.getDistanceWithUnit()
    }

    /**
     * @author M. Rose
     */
    override fun updateVisitedUI(landmarksVisited: Int) {
        landmarks_visited.text = landmarksVisited.toString()
    }

    override fun onDestroy() {
        //unsubscribe to cleanup event calls M. Rose
        EventBus.unsubscribeToStepEvent(this)
        EventBus.unsubscribeToLandmarkEvent(this)
        super.onDestroy()
    }
}