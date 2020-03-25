package com.group7.unveil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.group7.unveil.data.StepData
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkListener
import com.group7.unveil.events.StepListener
import kotlinx.android.synthetic.main.activity_main_page.*

/**
 * @author E Verdi
 */
class MainPage : Fragment(), StepListener,
    LandmarkListener {
    internal lateinit var navigationView: NavigationView
    internal lateinit var drawer: DrawerLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_main_page, container, false)

        EventBus.subscribeToLandmarkEvent(this)
        EventBus.subscribeToStepEvent(this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call the events on created to make the ui work properly M. Rose
        stepEvent(StepData.steps)
        updateVisitedUI(StepData.locationsVisited)
    }

    /**
     * @author M. Rose
     */
    override fun stepEvent(steps: Int) {
        step_count?.text = steps.toString()
        distance_actual?.text = StepData.getDistanceWithUnit()
    }

    /**
     * @author M. Rose
     */
    override fun updateVisitedUI(landmarksVisited: Int) {
        landmarks_visited?.text = landmarksVisited.toString()
    }

    override fun onDestroyView() {
        //should unsubscribe to speed up the app when the fragment goes off screen M. Rose
        EventBus.unsubscribeToStepEvent(this)
        EventBus.unsubscribeToLandmarkEvent(this)

        super.onDestroyView()
    }
}
