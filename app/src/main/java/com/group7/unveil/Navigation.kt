package com.group7.unveil

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.data.LocationData
import com.group7.unveil.data.SelectedRouteFromHome
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkEventData
import com.group7.unveil.events.MapSelectedEventData
import com.group7.unveil.events.UserMovedEventData
import com.group7.unveil.landmarks.LandmarkHeap
import com.group7.unveil.pages.MainPage
import com.group7.unveil.pages.Settings
import com.group7.unveil.util.ThemeHelper
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlin.math.roundToLong

/**
 * Contains the logic for navigating between pages of the app.
 * Also contains methods to update user location and related functions
 * @author M. Rose
 */
class Navigation : AppCompatActivity(), LocationListener {

    private val userMovedEventHandler : (UserMovedEventData) -> Unit = {updateVisitedCount()}
    private val mapSelectedEventHandler : (MapSelectedEventData) -> Unit = {switchToMap(it)}
    private val userSignedOutEventHandler : (Int?) -> Unit = {signOut()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(fragmentHost.id, MainPage()).commit()

        if(ThemeHelper.changedTheme)
        {
            ThemeHelper.changedTheme = false
            navListener.onNavigationItemSelected(bottomNavigation.menu.getItem(2))
            bottomNavigation.menu.getItem(2).isChecked = true
        }


        EventBus.userMovedEvent += userMovedEventHandler
        EventBus.changeToMap += mapSelectedEventHandler
        EventBus.userSignedOutEvent += userSignedOutEventHandler

        permissionGranted()
    }

    override fun onDestroy() {
        EventBus.userMovedEvent -= userMovedEventHandler
        EventBus.changeToMap -= mapSelectedEventHandler
        EventBus.userSignedOutEvent -= userSignedOutEventHandler
        super.onDestroy()
    }

    /**
     * logic for switching between pages using the bottom nav bar
     */
    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.navigation_home -> fragment = MainPage()
                R.id.navigation_user -> fragment = Settings()
                R.id.navigation_map -> fragment = com.group7.unveil.pages.Map()
            }

            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(fragmentHost.id, fragment).commit()
            }

            true
        }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionGranted()
        }
    }

    private fun permissionGranted()
    {
        Log.d("Location perms", "true")

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 123)
            return
        }

        LocationData.locationPermission = true
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0f, this)
    }

    /**
     * Calls to updated the number of visited landmarks
     */
    private fun updateVisitedCount() {
        if(LandmarkHeap.landmarkCanBeVisited() && !LandmarkHeap.peekTop().visited)
        {
            Log.d("Navigation", "Landmark ${LandmarkHeap.peekTop().name} Visited!")
            LandmarkHeap.peekTop().visited = true
            LocationData.locationsVisited++

            EventBus.landmarkEvent(LandmarkEventData(LocationData.locationsVisited))
        }
    }

    override fun onLocationChanged(p0: Location?) {
        if(p0 == null) return

        //get the user position to 4dp
        val userLoc = LatLng((p0.latitude * 10000.0).roundToLong() / 10000.0, (p0.longitude * 10000.0).roundToLong() /10000.0) // rounds to 4dp as any further is inaccurate

        //remake the heap so that it is correct for the new location
        LandmarkHeap.createMinHeap(userLoc)

        //call the event
        EventBus.userMovedEvent(UserMovedEventData(userLoc))
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        return
    }

    override fun onProviderEnabled(p0: String?) {
        Log.d("Provider Information", p0!!)
        return
    }

    override fun onProviderDisabled(p0: String?) {
        Log.d("Provider Information", p0!!)
        return
    }

    private fun switchToMap(selectedRoute : MapSelectedEventData){
        SelectedRouteFromHome.selectedRoute = selectedRoute.route
        navListener.onNavigationItemSelected(bottomNavigation.menu.getItem(1))
        bottomNavigation.menu.getItem(1).isChecked = true
    }

    fun signOut()
    {
        startActivity(Intent(this, LoginPage::class.java))
        finish()
    }
}
