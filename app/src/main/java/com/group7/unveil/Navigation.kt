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
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.data.LocationData
import com.group7.unveil.data.StepData
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkEventData
import com.group7.unveil.landmarks.LandmarkCounterHeap
import com.group7.unveil.pages.MainPage
import com.group7.unveil.pages.Settings
import com.group7.unveil.util.AppContext
import kotlinx.android.synthetic.main.activity_navigation.*

class Navigation : AppCompatActivity(), LocationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(fragmentHost.id, MainPage()).commit()

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
            Log.d("Location perms", "true")
            LocationData.locationPermission = true
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // premission has been granted android is being annoying
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0f, this)
        }
    }

    private fun updateVisitedCount() {
        if(LandmarkCounterHeap.landmarkCanBeVisited() && !LandmarkCounterHeap.peekTop().visited)
        {
//            Log.d("Navigation", "Landmark ${LandmarkCounterHeap.peekTop().name} Visited!")
            LandmarkCounterHeap.peekTop().visited = true
            LocationData.locationsVisited++

            EventBus.landmarkEvent(LandmarkEventData(LocationData.locationsVisited))
        }
    }

    override fun onLocationChanged(p0: Location?) {
        if(p0 == null) return

        val userLoc = LatLng(Math.round(p0.latitude * 10000.0) / 10000.0, Math.round(p0.longitude * 10000.0)/10000.0) // rounds to 4dp as any further is inaccurate
        LandmarkCounterHeap.createMinHeap(userLoc)

        updateVisitedCount()
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
