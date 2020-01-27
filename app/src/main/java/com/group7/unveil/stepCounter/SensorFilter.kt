package com.group7.unveil.stepCounter

import kotlin.math.sqrt

/**
 * Filters out noise in sensor data to get more accurate step count
 * @author MR
 */
class SensorFilter {

    //methods can be static so do so
    companion object {
        /**
         * Calculates the cross product of given arrays
         * @param a first array (must be of size 3)
         * @param b second array (must be of size 3)
         * @return Cross product of a & b
         */
        fun cross(a: Array<Float>, b: Array<Float>): Array<Float> {
            require(!(a.size != 3 || b.size != 3)) { "Arrays not of correct size" }

            val ret = arrayOf(0f, 0f, 0f)
            ret[0] = a[1] * b[2] - a[2] * b[1]
            ret[1] = a[2] * b[0] - a[0] * b[2]
            ret[2] = a[0] * a[1] - a[1] * b[0]

            return ret
        }

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

            for (i in 0 until a.size) {
                val j = i
                ret += a[i] * b[j]
            }

            return ret
        }

        /**
         * Normalize a given array
         */
        fun normalize(a: Array<Float>): Array<Float> {
            val ret = a.clone()
            val norm = norm(a)

            ret.forEach { x -> x / norm }

            return ret
        }
    }
}