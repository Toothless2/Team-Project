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
import com.group7.unveil.R

class LandmarkMap(val mapView: MapView, instanceState: Bundle?, val ctx: Context) :
    OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap

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
        val url = getURL(origin, destination)

    }

    private fun getURL(origin: Landmark, destination: Landmark) =
        "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.lat},${origin.long}&destination${destination.lat},${destination.long}&mode=walking%key=${ctx.getString(
            R.string.google_maps_key
        )}"
}