//TODO: comment

package com.group7.unveil.map

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.group7.unveil.R
import com.group7.unveil.map.RouteHelpers.FetchURL
import com.group7.unveil.map.RouteHelpers.TaskLoadedCallback

class LandmarkMap(val mapView: MapView, instanceState: Bundle?, val ctx: Context) :
    OnMapReadyCallback, GoogleMap.OnMarkerClickListener, TaskLoadedCallback {

    private lateinit var map: GoogleMap
    private lateinit var polyLine: Polyline

    init {
        mapView.onCreate(instanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        addLandmarks()

        map.moveCamera(CameraUpdateFactory.newLatLng(Landmarks.centre))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Landmarks.centre, 16f))
        map.setOnMarkerClickListener(this)

        // these speed up the map loading to a normal time for some reason
        mapView.onResume()
        mapView.onEnterAmbient(null)

        generateRoute(Landmarks.landmarks[0], Landmarks.landmarks[1])
    }

    fun addLandmarks() {
        for (place in Landmarks.landmarks) {
            val marker =
                map.addMarker(MarkerOptions().position(place.getLatLong()).title(place.name))
            marker.tag = place
        }
    }

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

    fun generateRoute(origin: Landmark, destination: Landmark) {
        //needs card info input into google and i am not doing that for the team project
//        val url = getURL(origin, destination)
//        var fetch = FetchURL(this).execute(url)

        val line = PolylineOptions().add(origin.getLatLong(), destination.getLatLong())
        map.addPolyline(line)

    }

    override fun onTaskDone(vararg values: Any) {

        polyLine = map.addPolyline(values[0] as PolylineOptions)
    }

    private fun getURL(origin: Landmark, destination: Landmark) =
        "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.lat},${origin.long}&destination=${destination.lat},${destination.long}&mode=walking%key=${ctx.getString(
            R.string.google_maps_key
        )}"
}