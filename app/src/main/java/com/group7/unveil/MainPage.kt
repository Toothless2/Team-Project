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
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.group7.unveil.data.StepData
import com.group7.unveil.stepCounter.StepDetector
import com.group7.unveil.stepCounter.StepListener
import kotlinx.android.synthetic.main.activity_main_page.*
import java.awt.font.NumericShaper

class MainPage : AppCompatActivity(), SensorEventListener, StepListener {

    lateinit var stepDetector: StepDetector
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    internal lateinit var signOut: Button
    internal lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        stepDetector = StepDetector()
        stepDetector.registerListener(this)

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        step(0)

        toMap.setOnClickListener { startActivity(Intent(this, Map::class.java)) }

        signOut = findViewById(R.id.signout)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        signOut.setOnClickListener { v ->
            when (v.id) {
                R.id.signout -> signOut()
            }
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {

                Toast.makeText(this@MainPage, "Successfully signed out", Toast.LENGTH_LONG).show()

                finish()
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

    override fun step(time: Long) {
        StepData.steps++
        step_count.text = StepData.steps.toString()
        distance_actual.text = StepData.getDistanceWithUnit()
    }
}
