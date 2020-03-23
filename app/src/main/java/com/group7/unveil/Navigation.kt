package com.group7.unveil

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.group7.unveil.data.StepData
import com.group7.unveil.stepCounter.LandmarkCounterHeap
import com.group7.unveil.stepCounter.StepDetector
import com.group7.unveil.stepCounter.StepListener
import com.group7.unveil.util.AppContext
import kotlinx.android.synthetic.main.activity_navigation.*

class Navigation : AppCompatActivity(), SensorEventListener, StepListener, LocationListener {

    companion object {
        lateinit var stepDetector: StepDetector
    }

    private lateinit var locationManager: LocationManager

    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )

        sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        stepDetector = StepDetector()
        stepDetector.registerListener(this)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)

        bottom_navigation.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(R.id.fragmetHolder, MainPage()).commit()
    }

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.nav_home -> fragment = MainPage()
                R.id.nav_user -> fragment = UserPage()
                R.id.nav_map -> fragment = Map()
            }

            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragmetHolder,
                    fragment
                ).commit()
            }

            true
        }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("Location perms", "true")
            AppContext.locationPerms = true
            locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // premission has been granted android is being annoying
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0f, this)
        }
    }

    override fun step() {
        StepData.steps++
    }

    override fun landmarkUpdate() {
        if(LandmarkCounterHeap.landmarkCanBeVisited() && !LandmarkCounterHeap.peekTop().visited)
        {
//            Log.d("Navigation", "Landmark ${LandmarkCounterHeap.peekTop().name} Visited!")
            LandmarkCounterHeap.peekTop().visited = true
            StepData.locationsVisited++
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
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

    override fun onLocationChanged(p0: Location?) {
        if(p0 == null) return

        val userLoc = LatLng(Math.round(p0.latitude * 10000.0) / 10000.0, Math.round(p0.longitude * 10000.0)/10000.0)
        LandmarkCounterHeap.createMinHeap(userLoc)

        stepDetector.updateLandmarks()
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        return
    }

    override fun onProviderEnabled(p0: String?) {
        Log.d("Proviider Infromation", p0!!)
        return
    }

    override fun onProviderDisabled(p0: String?) {
        Log.d("Provider Infomation", p0!!)
        return
    }
}