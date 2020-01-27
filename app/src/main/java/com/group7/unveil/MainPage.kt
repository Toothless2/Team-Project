package com.group7.unveil

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.group7.unveil.data.StepData
import com.group7.unveil.stepCounter.StepDetector
import com.group7.unveil.stepCounter.StepListener
import kotlinx.android.synthetic.main.activity_main_page.*
import java.awt.font.NumericShaper

class MainPage : AppCompatActivity(), SensorEventListener, StepListener {

    lateinit var stepDetector: StepDetector
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    var nSteps = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        stepDetector = StepDetector()
        stepDetector.registerListener(this)

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
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
        StepData.steps = ++nSteps
        step_count.text = StepData.steps.toString()
        distane_actual.text = StepData.getDistance().toString()
    }
}
