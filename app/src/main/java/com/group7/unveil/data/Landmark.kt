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
    var descriptor: String,
    var visited : Boolean = false
) {
    fun getLatLong() = LatLng(lat, long)
}

/**
 * Static container for landmarks in the app
 * @author Max Rose
 */
object Landmarks {
    /**
     * Default centre of the map, incase user location cannot be found
     */
    val centre = LatLng(54.9733026, -1.6249138)

    /**
     * All landmarks with information about them
     * @author M. Rose
     * @edited Alex Musgrove
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
        ),
        Landmark(
            3,
            AppContext.getContext().getString(R.string.millenbridge_name),
            54.970224,
            -1.600029,
            AppContext.getContext().getString(R.string.millenbridge_description)
        ),
        Landmark(
            4,
            AppContext.getContext().getString(R.string.vindolanda_name),
            54.991152,
            -2.360561,
            AppContext.getContext().getString(R.string.vindolanda_description)
        ),
        Landmark(
            5,
            AppContext.getContext().getString(R.string.thesill_name),
            54.995865,
            -2.388156,
            AppContext.getContext().getString(R.string.thesill_description)
        ),
        Landmark(
            6,
            AppContext.getContext().getString(R.string.kirkleyhall_name),
            55.089183,
            -1.766394,
            AppContext.getContext().getString(R.string.kirkleyhall_description)
        ),
        Landmark(
            7,
            AppContext.getContext().getString(R.string.kielder_name),
            55.234716,
            -2.580687,
            AppContext.getContext().getString(R.string.kielder_description)
        ),
        Landmark(
            8,
            AppContext.getContext().getString(R.string.whitehousefarm_name),
            55.130689,
            -1.702067,
            AppContext.getContext().getString(R.string.whitehousefarm_description)
        ),
        Landmark(
            9,
            AppContext.getContext().getString(R.string.romanarmym_name),
            54.985898,
            -2.520825,
            AppContext.getContext().getString(R.string.romanarmym_description)
        ),
        Landmark(
            10,
            AppContext.getContext().getString(R.string.woodhornm_name),
            55.189794,
            -1.547349,
            AppContext.getContext().getString(R.string.woodhornm_description)
        ),
        Landmark(
            11,
            AppContext.getContext().getString(R.string.morpethm_name),
            55.166868,
            -1.686752,
            AppContext.getContext().getString(R.string.morpethm_description)
        ),
        Landmark(
            12,
            AppContext.getContext().getString(R.string.alnwickc_name),
            55.415590,
            -1.705921,
            AppContext.getContext().getString(R.string.alnwickc_description)
        ),
        Landmark(
            13,
            AppContext.getContext().getString(R.string.hexhamabbey_name),
            54.971543,
            -2.102503,
            AppContext.getContext().getString(R.string.hexhamabbey_description)
        ),
        Landmark(
            14,
            AppContext.getContext().getString(R.string.bamburghc_name),
            55.608969,
            -1.709899,
            AppContext.getContext().getString(R.string.bamburghc_description)
        )
    )
}