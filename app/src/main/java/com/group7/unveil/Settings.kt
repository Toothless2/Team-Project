package com.group7.unveil

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.group7.unveil.data.StepData
import com.group7.unveil.stepCounter.StepDetector
import com.group7.unveil.stepCounter.StepListener
import kotlinx.android.synthetic.main.activity_user_page.*

class Settings : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    SensorEventListener, StepListener {

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
    lateinit var stepDetector: StepDetector
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    internal var language = arrayOf("English", "Polish", "German", "Bulgarian")
    internal var textSizes = arrayOf("Small", "Medium", "Big")
//    var PRIVATE_MODE = 0
//    val PREF_NAME = "com.example.group7.unveil"
//    val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
//    lateinit var fontSizePref: String
//    var themeID: Int = R.style.FontMedium



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.onActivityCreateSetTheme(this)

        setContentView(R.layout.settings)

//        fontSizePref = sharedPref.getString("FONT_SIZE", "Medium").toString()

        drawer = findViewById(R.id.drawer_layout)
        val set = findViewById<FloatingActionButton>(R.id.set)
        set.setOnClickListener { drawer.openDrawer(Gravity.RIGHT) }


        navigationView = findViewById(R.id.nav_view)
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
                Utils.changeToTheme(this, Utils.Dyslexic)
            } else if (!switch_id2.isChecked) {
                Utils.changeToTheme(this, Utils.LightTheme)

            }
        }

        val menuDarkTheme = menu.findItem(R.id.darkbutton)
        val actionViewDarkTh = MenuItemCompat.getActionView(menuDarkTheme)

        //button to turn dark mode
        dark = actionViewDarkTh.findViewById(R.id.imagebutt)
        dark.setOnClickListener {
            Utils.changeToTheme(this, Utils.DarkTheme)

        }

        val menuLightTheme= menu.findItem(R.id.whitebutton)
        val actionViewLightTh = MenuItemCompat.getActionView(menuLightTheme)

        //button to turn light mode
        light = actionViewLightTh.findViewById(R.id.imagebutt2)
        light.setOnClickListener {
            Utils.changeToTheme(this, Utils.LightTheme)
        }

        //spinner for languages
        val spinner = navigationView.menu.findItem(R.id.lang).actionView as Spinner
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, language)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                Toast.makeText(this@Settings, language[position], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        //spinner for text sizes
        val spinner2 = navigationView.menu.findItem(R.id.textsize).actionView as Spinner
        spinner2.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, textSizes)
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                when(position) {

                    1 -> Utils.changeToTheme(this@Settings, Utils.Medium)
                    2 -> Utils.changeToTheme(this@Settings, Utils.Big)
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

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        signOut.setOnClickListener { v ->
            when (v.id) {
                R.id.signoutbutton -> signOut()
            }
        }

        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.me)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        stepDetector = StepDetector()
        stepDetector.registerListener(this)

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        step(0)

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

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//
//        if (id == R.id.action_settings) {
//            drawer.openDrawer(GravityCompat.END)
//            return true
//        } else
//            return super.onOptionsItemSelected(item)
//    }


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
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            stepDetector.updateAccel(
                event.timestamp,
                event.values[0],
                event.values[1],
                event.values[2]
            )
        }
    }

    override fun step(time: Long) {
        StepData.steps++
        step_count1.text = StepData.steps.toString()
        distance_actual1.text = StepData.getDistanceWithUnit()
    }
}