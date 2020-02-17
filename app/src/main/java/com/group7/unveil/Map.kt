//TODO: comment

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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.group7.unveil.map.*
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

        val buttons = routeButtons()

        //the list of routes
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MapRecyclerAdaptor(buttons)
    }

    private fun routeButtons(): List<MapRouteButtonLayoutHolder> {
        val buttons = mutableListOf<MapRouteButtonLayoutHolder>()

        for (i in Routes.routeNames().indices)
            buttons.add(this.routeButton(Routes.routeNames()[i], Routes.routes[i]))

        return buttons
    }

    private fun routeButton(routeName: String, route: Route): MapRouteButtonLayoutHolder {
        val b = Button(recyclerView.context)
        b.text = routeName
        b.setOnClickListener { mapHelper?.generateRoute(route) }

        Log.d("Route button pressed", "Route $routeName wanted")

        return MapRouteButtonLayoutHolder(b)
    }

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
        }
    }

    private fun getLocation() {
        if (!permissions)
            return

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, this)
        } else
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    override fun onLocationChanged(loc: Location?) {
        mapHelper?.placeUser(loc!!)
        return
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
