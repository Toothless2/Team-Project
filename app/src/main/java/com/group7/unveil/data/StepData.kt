package com.group7.unveil.data

import android.widget.TextView
import kotlin.math.round

/**
 * @author Max Rose
 * Contains data for the step counter visible throughout the app
 */
class StepData {
    companion object {
        /**
         * Number of steps the user has done
         */
        var steps = 0

        /**
         * The distance the user has moved
         * @return The distnace in miles (assuming waking pase of 3mph)
         */
        fun getDistance() = round((steps * 0.00045) * 100.0) / 100.0

        /**
         * Distance user has traveled with unit appended
         */
        fun getDistanceWithUnit() = "${getDistance()} miles"

        /**
         * THe number of locations a user has visited
         */
        var locationsVisited = 0
    }
}