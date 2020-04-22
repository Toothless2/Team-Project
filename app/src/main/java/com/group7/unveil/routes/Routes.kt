package com.group7.unveil.data

import com.group7.unveil.R
import com.group7.unveil.util.AppContext

/**
 * Static container for all routes in the app
 * @author Max Rose
 * @edited Eldar Verdi
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
                Landmarks.landmarks[2],
                Landmarks.landmarks[1],
                Landmarks.landmarks[0]
            ),
            AppContext.appContext.getString(
                R.string.greysUSBviaSU_description
            )
        )
    )
}