package com.group7.unveil.util

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
        stepEventListeners.forEach { it.stepEvent(steps) }
    }

    fun subscribeToLandmarkEvent(listener: LandmarkListener) {
        if (landmarkEventListeners.contains(listener))
            return

        landmarkEventListeners.add(listener)
    }

    fun unsubscribeToLandmarkEvent(listener: LandmarkListener) = landmarkEventListeners.remove(listener)

    fun callLandmarkUIUpdate(landmarksVisited: Int) {
        landmarkEventListeners.forEach { it.updateVisitedUI(landmarksVisited) }
    }
}

// Event Interfaces, best practice would be to place in separate files but easier to read keeping them together since they arnt used for anything else
interface LandmarkListener
{
    fun updateVisitedCount() { return }
    fun updateVisitedUI(landmarksVisited : Int) { return }
}

interface StepListener {
    fun stepEvent(steps: Int)
}