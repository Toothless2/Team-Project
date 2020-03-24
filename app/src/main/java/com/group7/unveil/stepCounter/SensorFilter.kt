package com.group7.unveil.stepCounter

import kotlin.math.sqrt

/**
 * Filters out noise in sensor data to get more accurate step count
 * @author Adapted from a GitHub repo that I cant find anymore (M.Rose)
 */
object SensorFilter {
    /**
     * Bring all a values positive
     */
    fun norm(a: Array<Float>): Float {
        var ret = 0f

        for (v in a)
            ret += v * v

        return sqrt(ret)
    }

    /**
     * Gives dot product of given arrays
     */
    fun dot(a: Array<Float>, b: Array<Float>): Float {
        require(a.size == b.size) { "Arrays mus be the same size" }
        var ret = 0f

        for (i in a.indices)
            ret += a[i] * b[i]

        return ret
    }
}