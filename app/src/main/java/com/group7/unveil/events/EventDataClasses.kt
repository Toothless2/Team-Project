package com.group7.unveil.events

import com.group7.unveil.data.Landmarks

data class StepEventData(val steps : Int) { init { require(steps >= 0) { "Cannot have negative steps" } } }
data class LandmarkEventData(val landmarks : Int) {
    init {
        require(landmarks >= 0) { "Cannot have negative landmarks" }
        require(Landmarks.landmarks.size > landmarks) { "Cannot visit more landmarks than exist" }
    }
}