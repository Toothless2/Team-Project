package com.group7.unveil.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.group7.unveil.R
import com.group7.unveil.data.AccountInformation
import com.group7.unveil.data.LocationData
import com.group7.unveil.data.StepData
import com.group7.unveil.events.*
import com.group7.unveil.util.ThemeHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.app_bar_settings.*

/**
 * Logic for settings in the app
 * @author N.K. Chmurak
 */
class Settings : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private val mAppBarConfiguration: AppBarConfiguration? = null
    private lateinit var navigationView: NavigationView
    private lateinit var drawer: DrawerLayout
    private lateinit var switchId: SwitchCompat
    private lateinit var switchId2: SwitchCompat
    private lateinit var seekbar: SeekBar
    private lateinit var dark: ImageButton
    private lateinit var light: ImageButton
    private lateinit var signOut: Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    internal var language = arrayOf("English", "Polish", "German", "Bulgarian")
    private var textSizes = arrayOf("Small", "Medium", "Big")

    private val stepEventHandler: (StepEventData) -> Unit = { stepEvent(it.steps) }
    private val landmarkEventHandler: (LandmarkEventData) -> Unit =
        { updateVisitedUI(it.landmarks) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let { ThemeHelper.onActivityCreateSetTheme(it) }
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.settings, container, false)
//        super.onCreate(savedInstanceState)
        EventBus.stepEvent += stepEventHandler
        EventBus.landmarkEvent += landmarkEventHandler

        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.me)
        return rootView
    }

    @SuppressLint("RtlHardcoded")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAccountName()

        drawer = requireView().findViewById(R.id.drawer_layout)
        val set = view.findViewById<FloatingActionButton>(R.id.set)
        set.setOnClickListener { drawer.openDrawer(Gravity.RIGHT) }
        navigationView = requireView().findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val menu = navigationView.menu
        val menuColorBlind = menu.findItem(R.id.colorblind)
        val actionViewColorBlind = MenuItemCompat.getActionView(menuColorBlind)

        //colorblind mode switch
        switchId = actionViewColorBlind.findViewById(R.id.switch_id)
        //switch_id.isChecked = true
        switchId.setOnClickListener {

            if (switchId.isChecked) {
                //code to turn on colorblind mode
            } else if (!switchId.isChecked) {
                //code to turn off colorblind mode
            }
        }

        val menuDyslexic = menu.findItem(R.id.dyslex)
        val actionViewDyslexic = MenuItemCompat.getActionView(menuDyslexic)

        //dyslexic font switch
        switchId2 = actionViewDyslexic.findViewById(R.id.switch_id2)
        //switch_id2.isChecked = true
        switchId2.setOnClickListener {
            if (switchId2.isChecked) {
                activity?.let { it1 -> ThemeHelper.changeToTheme(it1, ThemeHelper.Dyslexic) }
            } else if (!switchId2.isChecked) {
                activity?.let { it1 -> ThemeHelper.changeToTheme(it1, ThemeHelper.LightTheme) }

            }
        }

        val menuDarkTheme = menu.findItem(R.id.darkbutton)
        val actionViewDarkTh = MenuItemCompat.getActionView(menuDarkTheme)

        //button to turn dark mode
        dark = actionViewDarkTh.findViewById(R.id.imagebutt)
        dark.setOnClickListener {
            activity?.let { it1 -> ThemeHelper.changeToTheme(it1, ThemeHelper.DarkTheme) }

        }

        val menuLightTheme = menu.findItem(R.id.whitebutton)
        val actionViewLightTh = MenuItemCompat.getActionView(menuLightTheme)

        //button to turn light mode
        light = actionViewLightTh.findViewById(R.id.imagebutt2)
        light.setOnClickListener {
            activity?.let { it1 -> ThemeHelper.changeToTheme(it1, ThemeHelper.LightTheme) }
        }

        //spinner for languages
        val spinner = navigationView.menu.findItem(R.id.lang).actionView as Spinner
        spinner.adapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item,
                    language
                )
            }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                Toast.makeText(context, language[position], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        //  spinner for text sizes
        val spinner2 = navigationView.menu.findItem(R.id.textsize).actionView as Spinner
        spinner2.adapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item,
                    textSizes
                )
            }
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                when (position) {

                    1 -> activity?.let { ThemeHelper.changeToTheme(it, ThemeHelper.Medium) }
                    2 -> activity?.let { ThemeHelper.changeToTheme(it, ThemeHelper.Big) }
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
        updateVisitedUI(LocationData.locationsVisited)
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

    @Suppress("LiftReturnOrAssignment")
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

        val drawer = view!!.findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

//     fun onSupportNavigateUp(): Boolean {
//        val navController = Navigation.findNavController(this.requireActivity(), R.id.nav_host_fragment)
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration!!) || onSupportNavigateUp()
//    }


    private fun signOut() {
        activity?.let {
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
    private fun stepEvent(steps: Int) {
        step_count1.text = steps.toString()
        distance_actual1.text = StepData.getDistanceWithUnit()
    }

    /**
     * @author M. Rose
     */
    private fun updateVisitedUI(landmarksVisited: Int) {
        landmarks_visited.text = landmarksVisited.toString()
    }

    /**
     * @author M. Rose
     */
    private fun setAccountName()
    {
        if(AccountInformation.account == null)
        {
            userDisplayName.text = ""
            avatar.setImageURI(null)
        }
        else
        {
            userDisplayName.text = AccountInformation.account!!.displayName
            Picasso.get().load(AccountInformation.getPhotoURI()).into(avatar)
        }
    }

    override fun onDestroy() {
        //unsubscribe to cleanup event calls M. Rose
        EventBus.stepEvent -= stepEventHandler
        EventBus.landmarkEvent -= landmarkEventHandler
        super.onDestroy()
    }
}