package com.group7.unveil.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.group7.unveil.R
import com.group7.unveil.data.LocationData
import com.group7.unveil.data.StepData
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkEventData
import com.group7.unveil.events.StepEventData
import com.group7.unveil.pages.alerts.ToursAlert
import com.group7.unveil.routes.Routes
import kotlinx.android.synthetic.main.main_page_fragment.*

/**
 * @author Eldar Verdi
 */
class MainPage : Fragment() {

    private val stepEventHandler: (StepEventData) -> Unit = { stepEvent(it.steps) }

    // Event handler for landmarks on the main page
    private val landmarkEventHandler: (LandmarkEventData) -> Unit =
        { updateVisitedUI(it.landmarks) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.main_page_fragment, container, false)

        EventBus.stepEvent += stepEventHandler
        EventBus.landmarkEvent += landmarkEventHandler

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call the events on created to make the ui work properly M. Rose
        stepEvent(StepData.steps)
        updateVisitedUI(LocationData.locationsVisited)

        tourOne.setOnClickListener {
            ToursAlert.openDialog(this, Routes[0])

        }

        tourTwo.setOnClickListener {
            ToursAlert.openDialog(this, Routes[1])
        }

        tourThree.setOnClickListener {
            ToursAlert.openDialog(this, Routes[2])
        }

        tourFour.setOnClickListener {
            ToursAlert.openDialog(this, Routes[3])
        }

    }

    /**
     * @author M. Rose
     */
    private fun stepEvent(steps: Int) {
        step_count?.text = steps.toString()
        distance_actual?.text = StepData.getDistanceWithUnit()
    }

    /**
     * @author M. Rose
     */
    private fun updateVisitedUI(landmarksVisited: Int) {
        landmarks_visited?.text = landmarksVisited.toString()
    }

    override fun onDestroyView() {
        //should unsubscribe to speed up the app when the fragment goes off screen M. Rose
        EventBus.stepEvent -= stepEventHandler
        EventBus.landmarkEvent -= landmarkEventHandler

        super.onDestroyView()
    }

}