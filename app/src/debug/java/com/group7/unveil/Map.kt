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
import com.google.android.gms.maps.MapView
import com.group7.unveil.map.*

/**
 * Map page activity
 * @author Max Rose
 */
class Map : AppCompatActivity(), LocationListener {
    private lateinit var map: LandmarkMap
    private var permissions: Boolean = false
    private lateinit var locationManager: LocationManager
    private var userLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )

        //creates the map
        map = LandmarkMap(
            findViewById(R.id.mapView),
            savedInstanceState,
            findViewById<MapView>(R.id.mapView).context
        )

        val buttons = routeButtons()

        //the list of routes
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = MapRecyclerAdaptor(buttons)
    }

    private fun routeButtons(): List<MapRouteButtonLayoutHolder> {
        val buttons = mutableListOf<MapRouteButtonLayoutHolder>()

        for (i in Routes.routeNames().indices)
            buttons.add(routeButton(Routes.routeNames()[i], Routes.routes[i]))

        return buttons
    }

    fun routeButton(routeName: String, route: Route): MapRouteButtonLayoutHolder {
        val b = Button(findViewById<RecyclerView>(R.id.recyclerView).context)
        b.text = routeName
        b.setOnClickListener { map.generateRoute(route) }

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
            map.locationPermissonDenied()
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
        map.placeUser(loc!!)
//        Log.d("Position", "Lat: ${loc!!.latitude} | Lng: ${loc!!.longitude}")
        return
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        return
    }

    override fun onProviderEnabled(p0: String?) {
        return
    }

    override fun onProviderDisabled(p0: String?) {

        Log.d("Location Infomation", "Provider Disabled")
        return
    }
}
