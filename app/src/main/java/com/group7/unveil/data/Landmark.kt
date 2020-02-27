package com.group7.unveil.data

import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.R
import com.group7.unveil.util.AppContext

/**
 * Contains a landmark for the app
 * @author Max Rose
 */
data class Landmark(
    val id: Int,
    var name: String,
    val lat: Double,
    val long: Double,
    var descriptor: String
) {
    fun getLatLong() = LatLng(lat, long)
}

/**
 * Static container for landmarks in the app
 * @author Max Rose
 */
class Landmarks {
    companion object {
        /**
         * Default centre of the map, incase user location cannot be found
         */
        val centre = LatLng(54.9733026, -1.6249138)
        /**
         * All landmarks with infomation about them
         * @note array is used instead of database as it overkill
         */
        val landmarks = arrayOf(
            Landmark(
                0,
                AppContext.getContext().getString(R.string.usb_name),
                54.973546,
                -1.6263303,
                AppContext.getContext().getString(R.string.usb_description)
            ),
            Landmark(
                1,
                AppContext.getContext().getString(R.string.su_name),
                54.9788463,
                -1.6141698,
                AppContext.getContext().getString(R.string.su_description)
            ),
            Landmark(
                2,
                AppContext.getContext().getString(R.string.greysmon_name),
                54.9741845,
                -1.6153915,
                AppContext.getContext().getString(R.string.greysmon_description)
            )
        )
    }
}