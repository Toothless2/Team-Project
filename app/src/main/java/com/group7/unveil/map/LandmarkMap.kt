package com.group7.unveil.map

import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.group7.unveil.R
import com.group7.unveil.map.RouteHelpers.RouteAPI.TaskLoadedCallback
import com.group7.unveil.map.RouteHelpers.RouteHeap

/**
 * Contains methods for the map
 * @author Max Rose
 */
class LandmarkMap(val map: GoogleMap, val ctx: Context) : GoogleMap.OnMarkerClickListener,
    TaskLoadedCallback {

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

            val dialog = AlertDialog.Builder(ctx)
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
        //needs card info input into google and i am not doing that for the team project
//        val url = getURL(origin, destination)
//        var fetch = FetchURL(this).execute(url)

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

    override fun onTaskDone(vararg values: Any) {
        polyLine = map.addPolyline(values[0] as PolylineOptions)
    }

    private fun getURL(origin: Landmark, destination: Landmark) =
        "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.lat},${origin.long}&destination=${destination.lat},${destination.long}&mode=walking%key=${ctx.getString(
            R.string.google_maps_key
        )}"

    /**
     * Updates the route heap and remakes the buttons
     */
    fun updateRouteHeap(userLocation: LatLng) {
        RouteHeap.createMinHeap(LatLng(userLocation.latitude, userLocation.longitude))

        (ctx as com.group7.unveil.Map).updateRouteButtons()
    }

    fun locationPermissonDenied() {
        map.isMyLocationEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
    }
}