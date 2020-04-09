package com.group7.unveil.stepCounter

import kotlin.math.sqrt

/**
 * Filters out noise in sensor data to get more accurate step count
 */
object SensorFilter {
    /**
     * Normalizes given vector
     * @author M. Rose
     */
    fun norm(a: Array<Float>) = sqrt(a.map { it * it }.sum())

    /**
     * Gives dot product of given arrays
     * @author M. Rose
     */
    fun dot(a: Array<Float>, b: Array<Float>): Float {
        require(a.size == b.size) { "Arrays mus be the same size" }
        return a.zip(b).map { it.first * it.second }.sum()
    }
}