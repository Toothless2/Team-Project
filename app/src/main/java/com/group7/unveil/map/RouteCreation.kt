package com.group7.unveil.map

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.group7.unveil.R
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.MapSelectedEventData
import com.group7.unveil.landmarks.Landmark
import com.group7.unveil.landmarks.Landmarks
import com.group7.unveil.routes.Route
import com.group7.unveil.routes.Routes
import com.group7.unveil.util.DistanceHelper
import kotlinx.android.synthetic.main.route_creation_fragment.*
import kotlin.math.round

/**
 * Controls Creation of routes
 * @author M. Rose
 */
class RouteCreation(private val map : LandmarkMap) : Fragment() {

    private val landmarkList = mutableListOf<Landmark>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.route_creation_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createRouteDistanceDisplay.text = "${createRouteDistanceDisplay.text}: 0.0 miles"

        landmarkSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, Landmarks.copyOf().map { it.name })
        addLandmarkToRoute.setOnClickListener {
            addLandmark(landmarkSpinner.selectedItem as String)
        }

        routeCreationStartRoute.setOnClickListener {
            startRoute()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addLandmark(landmarkName : String)
    {
        createdRouteDisplay.text = "${createdRouteDisplay.text}$landmarkName\n"

        landmarkList.add(Landmarks.copyOf().find { it.name == landmarkName }!!)

        if(landmarkList.size > 1)
            createRouteDistanceDisplay.text = "${getString(R.string.totalDistance)}: ${totalRouteDistance()} miles"
    }

    private fun totalRouteDistance() : Double
    {
        return round((landmarkList.zipWithNext { a, b -> DistanceHelper.getDistance(a.getLatLong(), b.getLatLong()) }.sum() * 0.000621371) * 100) / 100 // get the distance and scale to 2dp
    }

    private fun startRoute() {

        lateinit var route : Route

        if (landmarkList.size > 1) {
            route = Route(landmarkList, "Custom Route")
        }
        else
            return

        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        EventBus.changeToMap(MapSelectedEventData(route))
    }
}