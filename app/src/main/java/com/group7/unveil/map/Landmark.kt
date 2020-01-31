//TODO: comment

package com.group7.unveil.map

import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.R
import com.group7.unveil.util.AppContext

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
            Landmark(
                0,
                AppContext.getContext().getString(R.string.usb_name),
                54.973546,
                -1.6263303,
                AppContext.getContext().getString(R.string.usb_description)
            )
        )
    }
}