package com.group7.unveil

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.data.Landmarks
import com.group7.unveil.data.Route
import com.group7.unveil.data.SelectedRouteFromHome
import com.group7.unveil.map.*
import com.group7.unveil.map.routeHelpers.RouteHeap
import com.group7.unveil.util.AppContext
import kotlinx.android.synthetic.main.activity_map.*
import kotlin.math.log

/**
 * Map page activity
 * @author M. Rose
 */
class Map : Fragment(), LocationListener, OnMapReadyCallback {
    private var mapHelper: LandmarkMap? = null
    private lateinit var map: GoogleMap
    private lateinit var locationManager: LocationManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.activity_map, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //makes the map
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        createRouteButton.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().add(id, RouteCreatetion(mapHelper!!)).addToBackStack(null).commit()
        }
    }

    /**
     * Adds buttons for routes to the pull up menu
     */
    fun updateRouteButtons() {
        if(recyclerView == null)
            return

        val buttons = routeButtons()

        //the list of routes
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MapRecyclerAdaptor(buttons)
    }

    /**
     * Creates the list of buttons for routes
     */
    private fun routeButtons(): List<MapRouteButtonModel> {
        val buttons = mutableListOf<MapRouteButtonModel>()

        for (i in RouteHeap.heap)
            buttons.add(this.routeButton(i))

        return buttons
    }

    /**
     * Creates a button for a route given a route and its name
     */
    private fun routeButton(route: Route): MapRouteButtonModel {
        val b = Button(recyclerView.context)
        b.text = route.getName()
        b.setOnClickListener { mapHelper?.generateRoute(route) }

        return MapRouteButtonModel(b)
    }

    /**
     * sets Map location stuff
     */
    private fun mapLocationPerms() {
        map.isMyLocationEnabled = AppContext.locationPerms
        map.uiSettings.isMyLocationButtonEnabled = AppContext.locationPerms
        getLocation()

        //still need to add the route buttons so use the map centre
        if (!AppContext.locationPerms)
            mapHelper?.updateRouteHeap(Landmarks.centre)
    }

    /**
     * Set listeners for the users location if permission has been granted
     */
    private fun getLocation() {
        if (!AppContext.locationPerms)
            return

        locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && context!!.checkSelfPermission(
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
                loc.longitude
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

        mapLocationPerms()

        map.moveCamera(CameraUpdateFactory.newLatLng(Landmarks.centre))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Landmarks.centre, 16f))
        map.setOnMarkerClickListener(mapHelper)

        mapHelper = LandmarkMap(map, this)
        mapHelper!!.addLandmarks()

        mapHelper?.updateRouteHeap(Landmarks.centre)

        if (SelectedRouteFromHome.selectedRoute != null)
            mapHelper!!.generateRoute(SelectedRouteFromHome.selectedRoute!!)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}
