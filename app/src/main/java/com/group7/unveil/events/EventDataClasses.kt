package com.group7.unveil.events

import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.landmarks.Landmarks
import com.group7.unveil.routes.Route

data class StepEventData(val steps : Int) { init { require(steps >= 0) { "Cannot have negative steps" } } }
data class LandmarkEventData(val landmarks : Int) {
    init {
        require(landmarks >= 0) { "Cannot have negative landmarks" }
        require(Landmarks.size > landmarks) { "Cannot visit more landmarks than exist" }
    }
}
data class UserMovedEventData(val location : LatLng)

data class MapSelectedEventData(val route : Route)