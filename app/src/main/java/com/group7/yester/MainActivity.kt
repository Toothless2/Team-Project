package com.group7.yester

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author M Rose
 * Homepage activity for app
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButtonExplore()
        setButtonTimePeriods()
        setButtonLandmarkMap()
    }

    /**
     * Sets behaviour for explore button
     */
    fun setButtonExplore()
    {
        explore.setOnClickListener { startActivity(Intent(this, explore::class.java)) }
    }

    /**
     * Sets behaviour for time period button
     */
    fun setButtonTimePeriods()
    {
        timeP.setOnClickListener { startActivity(Intent(this, timepreiods::class.java)) }
    }

    /**
     * Sets behaviour for landmark map button
     */
    fun setButtonLandmarkMap()
    {
        map.setOnClickListener { startActivity(Intent(this, LandmarkMap::class.java)) }
    }
}
