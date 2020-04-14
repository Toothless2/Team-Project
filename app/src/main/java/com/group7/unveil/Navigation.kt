package com.group7.unveil

import android.annotation.SuppressLint
import android.content.Context
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
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkEventData
import com.group7.unveil.events.UserMovedEventData
import com.group7.unveil.landmarks.LandmarkHeap
import com.group7.unveil.pages.MainPage
import com.group7.unveil.pages.Settings
import kotlinx.android.synthetic.main.activity_navigation.*
import java.util.jar.Manifest

class Navigation : AppCompatActivity(), LocationListener {

    private val userMovedEventHandler : (UserMovedEventData) -> Unit = {updateVisitedCount()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(fragmentHost.id, MainPage()).commit()

        EventBus.userMovedEvent += userMovedEventHandler

        permissonGranted()
    }

    override fun onDestroy() {
        EventBus.userMovedEvent -= userMovedEventHandler
        super.onDestroy()
    }

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
            permissonGranted()
        }
    }

    private fun permissonGranted()
    {
        Log.d("Location perms", "true")

        // premission has been granted android is being annoying
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 123)
            return
        }

        LocationData.locationPermission = true
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0f, this)
    }

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

        val userLoc = LatLng(Math.round(p0.latitude * 10000.0) / 10000.0, Math.round(p0.longitude * 10000.0)/10000.0) // rounds to 4dp as any further is inaccurate
        LandmarkHeap.createMinHeap(userLoc)
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
}
