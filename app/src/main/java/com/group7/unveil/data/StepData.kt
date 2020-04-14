package com.group7.unveil.data

import kotlin.math.round

/**
 * @author Max Rose
 * Contains data for the step counter visible throughout the app
 */
object StepData {
    /**
     * Number of steps the user has done
     */
    var steps = 0
        set(value) {
            require(value >= 0) { "Steps cannot be negative "}
            field = value
        }

    /**
     * The distance the user has moved
     * @return The distance in miles (assuming waking pace of 3mph)
     */
    fun getDistance() = round((steps * 0.00045) * 100.0) / 100.0

    /**
     * Distance user has traveled with unit appended
     */
    fun getDistanceWithUnit() = "${getDistance()} miles"
}