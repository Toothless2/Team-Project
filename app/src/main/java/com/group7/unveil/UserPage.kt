package com.group7.unveil

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.provider.ContactsContract
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import com.group7.unveil.data.StepData
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.navigation.NavigationView
import com.group7.unveil.stepCounter.StepDetector
import com.group7.unveil.stepCounter.StepListener
import kotlinx.android.synthetic.main.activity_main_page.*
import java.awt.font.NumericShaper

import kotlinx.android.synthetic.main.activity_user_page.*

class UserPage : AppCompatActivity(), SensorEventListener, StepListener {

    lateinit var stepDetector: StepDetector
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page)


        //setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        //val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.me)
        //constraintLayout.addView(imageView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        stepDetector = StepDetector()
        stepDetector.registerListener(this)

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        step(0)


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
