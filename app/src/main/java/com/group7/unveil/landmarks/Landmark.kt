package com.group7.unveil.data

import com.google.android.gms.maps.model.LatLng

/**
 * Contains a landmark for the app
 * @author Max Rose
 */
data class Landmark(
    val id: Int,
    var name: String,
    val lat: Double,
    val long: Double,
    var descriptor: String,
    var visited : Boolean = false
) {
    fun getLatLong() = LatLng(lat, long)
}