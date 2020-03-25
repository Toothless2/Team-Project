package com.group7.unveil

import android.os.Bundle
import android.widget.ImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.group7.unveil.data.StepData
import androidx.fragment.app.Fragment
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkListener
import com.group7.unveil.events.StepListener

import kotlinx.android.synthetic.main.activity_user_page.*

class UserPage : Fragment(), StepListener,
    LandmarkListener {

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
        EventBus.subscribeToStepEvent(this)
        EventBus.subscribeToLandmarkEvent(this)

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
    override fun stepEvent(steps: Int) {
        step_count1?.text = steps.toString()
        distance_actual1?.text = StepData.getDistanceWithUnit()
    }

    /**
     * @author M. Rose
     */
    override fun updateVisitedUI(landmarksVisited: Int) {
        landmarks_visited?.text = landmarksVisited.toString()
    }

    override fun onDestroyView() {
        // unsubscribe to cleanup event calls M. Rose
        EventBus.unsubscribeToStepEvent(this)
        EventBus.unsubscribeToLandmarkEvent(this)

        super.onDestroyView()
    }
}
