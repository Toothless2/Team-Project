package com.group7.unveil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.group7.unveil.data.StepData
import com.group7.unveil.stepCounter.StepListener
import kotlinx.android.synthetic.main.activity_main_page.*

/**
 * @author E Verdi
 * @edited M Rose
 */
class MainPage : Fragment(), StepListener {
    internal lateinit var navigationView: NavigationView
    internal lateinit var drawer: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_main_page, container, false)

        Navigation.stepDetector.registerListener(this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        step()
        landmarkUpdate()
    }

    override fun step() {
        step_count?.text = StepData.steps.toString()
        distance_actual?.text = StepData.getDistanceWithUnit()
    }

    override fun landmarkUpdate() {
        landmarks_visited?.text = StepData.locationsVisited.toString()
    }
}
