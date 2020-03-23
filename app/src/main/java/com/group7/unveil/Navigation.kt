package com.group7.unveil

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.group7.unveil.data.StepData
import com.group7.unveil.stepCounter.StepDetector
import com.group7.unveil.stepCounter.StepListener
import com.group7.unveil.util.AppContext
import kotlinx.android.synthetic.main.activity_navigation.*

class Navigation : AppCompatActivity(), SensorEventListener, StepListener {

    companion object {
        lateinit var stepDetector: StepDetector
    }

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("Location perms", "true")
            AppContext.locationPerms = true
        }
    }

    override fun step() {
        StepData.steps++
    }

    override fun locationChecker() {
        TODO("Not yet implemented")
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
}