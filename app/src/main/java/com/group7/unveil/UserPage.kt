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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.group7.unveil.data.StepData
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import com.group7.unveil.stepCounter.StepDetector
import com.group7.unveil.stepCounter.StepListener
import com.group7.unveil.util.AppContext
import java.awt.font.NumericShaper

import kotlinx.android.synthetic.main.activity_user_page.*

class UserPage : Fragment(), StepListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.activity_user_page, container, false)
        //setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        //val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.me)
        //constraintLayout.addView(imageView)

        Navigation.stepDetector.registerListener(this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        step()
        landmarkUpdate()
    }

    override fun step() {
        step_count1?.text = StepData.steps.toString()
        distance_actual1?.text = StepData.getDistanceWithUnit()
    }

    override fun landmarkUpdate() {
        landmarks_visited?.text = StepData.steps.toString()
    }
}
