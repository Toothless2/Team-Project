//TODO: comment

package com.group7.unveil.map

import com.google.android.gms.maps.model.LatLng

data class Landmark(
    val id: Int,
    var name: String,
    val lat: Double,
    val long: Double,
    var descriptor: String
) {
    fun getLatLong() = LatLng(lat, long)
}

class Landmarks {
    companion object {
        val centre = LatLng(54.9733026, -1.6249138)
        val landmarks = arrayOf(
            Landmark(0, "USB", 54.973546, -1.6263303, "The Glorious USB")
        )
    }
}