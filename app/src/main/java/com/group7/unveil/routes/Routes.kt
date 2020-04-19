package com.group7.unveil.routes

import com.group7.unveil.landmarks.Landmarks

/**
 * Static container for all routes in the app
 * @author M. Rose
 */
object Routes {
    /**
     * Routes in the app
     */
    private val routes = mutableListOf(
        Route(
            listOf(
                Landmarks[0],
                Landmarks[1]
            ), "USB -> SU"
        ),
        Route(
            listOf(
                Landmarks[1],
                Landmarks[0]
            ), "SU -> USB"
        ),
        Route(
            listOf(
                Landmarks[2],
                Landmarks[0]
            ), "Greys -> USB"
        ),
        Route(
            listOf(
                Landmarks[2],
                Landmarks[1],
                Landmarks[0]
            ),
            "Greys -> USB via SU"
        )
    )

    fun copyOf() = routes.toTypedArray().copyOf().toMutableList()

    operator fun get(i : Int) = routes[i]
    operator fun contains(r : Route) = r in routes;
    operator fun plusAssign(r : Route)
    {
        routes.add(r)
    }
}