package com.group7.unveil.data

import com.google.android.gms.maps.model.LatLng

/**
 * Contains a route for the app consisting of a list of landmarks
 * @author Max Rose
 */
data class Route(val landmarks: List<Landmark>, val description: String) {
    fun getFirst() = landmarks.first()
    fun getLast() = landmarks.last()
    fun getSize() = landmarks.size

    fun getStartPos(): LatLng {
        return landmarks[0].getLatLong()
    }

    /**
     * Creates a name for a given route
     */
    fun getName() : String =
        "${getFirst().name} -> ${getLast().name} ${landmarks.size.takeIf { it > 2 }
            ?.let { "(via ${landmarks[getSize() / 2].name})" }
            ?: ""}"
}

/**
 * Static container for all routes in the app
 * @author Max Rose
 */
object Routes {
    /**
     * Routes in the app
     */
    val routes = arrayOf(
        Route(
            listOf(
                Landmarks.landmarks[0],
                Landmarks.landmarks[1]
            ), "USB -> SU"
        ),
        Route(
            listOf(
                Landmarks.landmarks[1],
                Landmarks.landmarks[0]
            ), "SU -> USB"
        ),
        Route(
            listOf(
                Landmarks.landmarks[2],
                Landmarks.landmarks[0]
            ), "Greys -> USB"
        ),
        Route(
            listOf(
                Landmarks.landmarks[2],
                Landmarks.landmarks[1],
                Landmarks.landmarks[0]
            ),
            "Greys -> USB via SU"
        )
    )
}