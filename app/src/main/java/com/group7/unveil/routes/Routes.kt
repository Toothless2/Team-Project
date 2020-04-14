package com.group7.unveil.data

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