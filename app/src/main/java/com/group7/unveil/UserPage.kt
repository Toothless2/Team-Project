package com.group7.unveil

import android.os.Bundle
import android.widget.ImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.group7.unveil.data.StepData
import androidx.fragment.app.Fragment
import com.group7.unveil.events.*

import kotlinx.android.synthetic.main.activity_user_page.*

class UserPage : Fragment() {

    private val stepEventHandler : (StepEventData) -> Unit = { stepEvent(it.steps) }
    private val landmarkEventHandler : (LandmarkEventData) -> Unit = { updateVisitedUI(it.landmarks) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

        //subscribe to events
        EventBus.stepEvent += stepEventHandler
        EventBus.landmarkEvent += landmarkEventHandler

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // update the ui M. Rose
        stepEvent(StepData.steps)
        updateVisitedUI(StepData.locationsVisited)
    }

    /**
     * @author M. Rose
     */
    private fun stepEvent(steps: Int) {
        step_count1?.text = steps.toString()
        distance_actual1?.text = StepData.getDistanceWithUnit()
    }

    /**
     * @author M. Rose
     */
    private fun updateVisitedUI(landmarksVisited: Int) {
        landmarks_visited?.text = landmarksVisited.toString()
    }

    override fun onDestroyView() {
        // unsubscribe to cleanup event calls M. Rose
        EventBus.stepEvent -= stepEventHandler
        EventBus.landmarkEvent -= landmarkEventHandler

        super.onDestroyView()
    }
}
