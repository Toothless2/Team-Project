//TODO: comment

package com.group7.unveil

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.group7.unveil.data.StepData
import com.group7.unveil.stepCounter.StepDetector
import com.group7.unveil.stepCounter.StepListener
import com.group7.unveil.util.AppContext
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.coroutines.*

/**
 * @author E Verdi
 * @edited M Rose
 */
class MainPage : Fragment(), SensorEventListener, StepListener {

    lateinit var stepDetector: StepDetector
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor

    internal lateinit var navigationView: NavigationView
    internal lateinit var drawer: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_main_page, container, false)

        sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        stepDetector = StepDetector()
        stepDetector.registerListener(this)

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        step(0)
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

    override fun step(time: Long) {
        StepData.steps++
        step_count?.text = StepData.steps.toString()
        distance_actual?.text = StepData.getDistanceWithUnit()
    }


}
