package com.group7.unveil.routes

import com.group7.unveil.R
import com.group7.unveil.util.AppContext

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
                Landmarks.landmarks[0],
                Landmarks.landmarks[1]
            ), AppContext.appContext.getString(
                R.string.usbSU_description
            )
        ),
        Route(
            listOf(
                Landmarks.landmarks[1],
                Landmarks.landmarks[0]
            ), AppContext.appContext.getString(
                R.string.SUUSB_description
            )
        ),
        Route(
            listOf(
                Landmarks.landmarks[2],
                Landmarks.landmarks[0]
            ), AppContext.appContext.getString(
                R.string.greysUSB_description
            )
        ),
        Route(
            listOf(
                Landmarks[2],
                Landmarks[1],
                Landmarks[0]
            ),
            AppContext.appContext.getString(
                R.string.greysUSBviaSU_description
            )
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