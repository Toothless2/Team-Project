package com.group7.unveil.map

import com.google.android.gms.maps.model.LatLng

/**
 * Contains a route for the app consisting of a list of landmarks
 * @author Max Rose
 */
data class Route(val landmarks: List<Landmark>, val description: String) {
    fun getStartPos(): LatLng {
        return landmarks[0].getLatLong()
    }
}

/**
 * Static container for all routes in the app
 * @author Max Rose
 */
class Routes {
    companion object {
        /**
         * Routes in the app
         */
        val routes = arrayOf(
            Route(listOf(Landmarks.landmarks[0], Landmarks.landmarks[1]), "USB -> SU"),
            Route(listOf(Landmarks.landmarks[1], Landmarks.landmarks[0]), "SU -> USB"),
            Route(listOf(Landmarks.landmarks[2], Landmarks.landmarks[0]), "Greys -> USB"),
            Route(
                listOf(Landmarks.landmarks[2], Landmarks.landmarks[1], Landmarks.landmarks[0]),
                "Greys -> USB via SU"
            )
        )

        fun routeNames(): List<String> {
            val n = mutableListOf<String>()

            for (r in routes) {
                n.add("${r.landmarks[0].name} -> ${r.landmarks[r.landmarks.size - 1].name}")
            }

            return n
        }
    }
}