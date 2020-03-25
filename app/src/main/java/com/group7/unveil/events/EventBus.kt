package com.group7.unveil.events

/**
 * Event Bus using interfaces for updating things throughout the app
 * @author M. Rose
 */
object EventBus {
    private var stepEventListeners = mutableListOf<StepListener>()
    private var landmarkEventListeners = mutableListOf<LandmarkListener>()

    fun subscribeToStepEvent(listener: StepListener) {
        if (stepEventListeners.contains(listener))
            return

        stepEventListeners.add(listener)
    }

    fun unsubscribeToStepEvent(listener: StepListener) = stepEventListeners.remove(listener)

    fun callStepEvent(steps : Int) {
        require(steps >= 0) { "Cannot have negative steps" }

        stepEventListeners.forEach { it.stepEvent(steps) }
    }

    fun subscribeToLandmarkEvent(listener: LandmarkListener) {
        if (landmarkEventListeners.contains(listener))
            return

        landmarkEventListeners.add(listener)
    }

    fun unsubscribeToLandmarkEvent(listener: LandmarkListener) = landmarkEventListeners.remove(listener)

    fun callLandmarkUIUpdate(landmarksVisited: Int) {
        require(landmarksVisited >= 0) { "Cannot have negative landmarks"}
//        require(landmarksVisited <= Landmarks.landmarks.size) { "Cannot visit more landmarks than exist!" }

        landmarkEventListeners.forEach { it.updateVisitedUI(landmarksVisited) }
    }
}

