package com.group7.yester

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButtonExplore()
        setButtonTimePeriods()
        setButtonLandmarkMap()
    }

    fun setButtonExplore()
    {
        explore.setOnClickListener { startActivity(Intent(this, explore::class.java)) }
    }

    fun setButtonTimePeriods()
    {
        timeP.setOnClickListener { startActivity(Intent(this, timepreiods::class.java)) }
    }

    fun setButtonLandmarkMap()
    {
        map.setOnClickListener { startActivity(Intent(this, LandmarkMap::class.java)) }
    }
}
