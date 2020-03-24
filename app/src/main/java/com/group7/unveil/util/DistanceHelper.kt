package com.group7.unveil.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object DistanceHelper {
    /**
     * Gets the distance between a given point and the user
     * @author Adapted from https://www.movable-type.co.uk/scripts/latlong.html by M. Rose
     */
    fun getDistace(pos1: LatLng, pos2: LatLng): Double {
        val r = 6371e3
        val l1 = Math.toRadians(pos1.latitude)
        val l2 = Math.toRadians(pos2.latitude)
        val deltaRoh = Math.toRadians(pos2.latitude - pos1.latitude)
        val deltaLambda = Math.toRadians(pos2.longitude - pos1.longitude)

        val a = sin(deltaRoh / 2) * sin(deltaRoh) +
                cos(l1) * cos(l2) *
                sin(deltaLambda / 2) * sin(deltaLambda / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }
}