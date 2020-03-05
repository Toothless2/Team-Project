package com.group7.unveil.map

import android.app.AlertDialog
import android.util.Log
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.group7.unveil.data.Landmark
import com.group7.unveil.data.Landmarks
import com.group7.unveil.data.Route
import com.group7.unveil.map.RouteHelpers.RouteHeap

/**
 * Contains methods for the map
 * @author Max Rose
 */
class LandmarkMap(val map: GoogleMap, val parentActivity: com.group7.unveil.Map) :
    GoogleMap.OnMarkerClickListener {

    private lateinit var polyLine: Polyline
    private var userMarker: Marker? = null
    private var line = PolylineOptions()

    /**
     * Adds the landmarks to the map
     */
    fun addLandmarks() {
        for (place in Landmarks.landmarks) {
            val marker =
                map.addMarker(MarkerOptions().position(place.getLatLong()).title(place.name))
            marker.tag = place
        }
    }

    /**
     * Shows a dialog with marker information after clicking on a marker
     */
    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag

        if (tag != null) {
            val mark = tag as Landmark
            Log.d("Marker Clicked", "Marker ${mark.name} Clicked, Description: ${mark.descriptor}")

            val dialog = AlertDialog.Builder(parentActivity.context)
            dialog.setTitle(mark.name).setMessage(mark.descriptor)

            dialog.create()
            dialog.show()
        }

        return false
    }

    /**
     * Adds the selected route line to the map
     */
    fun generateRoute(route: Route) {

        Log.d("Generate Route", "Generate Route: ${route.description}")

        map.clear()
        if (userMarker != null) {
            userMarker =
                map.addMarker(MarkerOptions().position(userMarker!!.position).title(userMarker!!.title))
            userMarker!!.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        }
        addLandmarks()
        line.points.clear()

        route.landmarks.forEach { l -> line.add(l.getLatLong()) }

        map.addPolyline(line)

    }

    /**
     * Updates the route heap and remakes the buttons
     */
    fun updateRouteHeap(userLocation: LatLng) {
        RouteHeap.createMinHeap(LatLng(userLocation.latitude, userLocation.longitude))

        parentActivity.updateRouteButtons()
    }
}