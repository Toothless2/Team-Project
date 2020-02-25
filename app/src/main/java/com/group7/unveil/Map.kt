package com.group7.unveil

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.map.*
import com.group7.unveil.map.RouteHelpers.RouteHeap
import kotlinx.android.synthetic.main.activity_map.*

/**
 * Map page activity
 * @author Max Rose
 */
class Map : AppCompatActivity(), LocationListener, OnMapReadyCallback {
    private var mapHelper: LandmarkMap? = null
    private lateinit var map: GoogleMap
    private var permissions: Boolean = false
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )

        //creates the map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    /**
     * Adds buttons for routes to the pull up menu
     */
    fun updateRouteButtons() {
        val buttons = routeButtons()

        //the list of routes
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MapRecyclerAdaptor(buttons)
    }

    /**
     * Creates the list of buttons for routes
     */
    private fun routeButtons(): List<MapRouteButtonModel> {
        val buttons = mutableListOf<MapRouteButtonModel>()

        for (i in RouteHeap.getHeap())
            buttons.add(this.routeButton(Routes.routeName(i), i))

        return buttons
    }

    /**
     * Creates a button for a route given a route and its name
     */
    private fun routeButton(routeName: String, route: Route): MapRouteButtonModel {
        val b = Button(recyclerView.context)
        b.text = routeName
        b.setOnClickListener { mapHelper?.generateRoute(route) }

        return MapRouteButtonModel(b)
    }

    /**
     * Request permissions to use the users location
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Log.d("Location perms",  "true")
            this.permissions = true
            getLocation()
        } else {
            this.permissions = false
            //still need to add the route buttons so use the map centre
            mapHelper?.updateRouteHeap(Landmarks.centre)
        }
    }

    /**
     * Set listensers for the users location if permission has been granted
     */
    private fun getLocation() {
        if (!permissions)
            return

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0f, this)
        } else
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    override fun onLocationChanged(loc: Location?) {
        mapHelper?.updateRouteHeap(
            LatLng(
                loc!!.latitude,
                loc!!.longitude
            )
        )  // use to update the route heap
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        return
    }

    override fun onProviderEnabled(p0: String?) {
        Log.d("Proviider Infromation", p0!!)
        return
    }

    override fun onProviderDisabled(p0: String?) {
        Log.d("Provider Infomation", p0!!)
        return
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        map.moveCamera(CameraUpdateFactory.newLatLng(Landmarks.centre))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Landmarks.centre, 16f))
        map.setOnMarkerClickListener(mapHelper)

        mapHelper = LandmarkMap(map, this)
        mapHelper?.addLandmarks()
    }

    override fun onResume() {
        super.onResume()
        findViewById<MapView>(R.id.mapView).onResume()
    }

    override fun onStart() {
        super.onStart()
        findViewById<MapView>(R.id.mapView).onStart()
    }

    override fun onStop() {
        super.onStop()
        findViewById<MapView>(R.id.mapView).onStop()
    }

    override fun onPause() {
        super.onPause()
        findViewById<MapView>(R.id.mapView).onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        findViewById<MapView>(R.id.mapView).onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        findViewById<MapView>(R.id.mapView).onLowMemory()
    }
}
