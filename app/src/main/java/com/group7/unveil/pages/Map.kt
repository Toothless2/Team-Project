package com.group7.unveil.pages

import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.group7.unveil.R
import com.group7.unveil.landmarks.Landmarks
import com.group7.unveil.data.LocationData
import com.group7.unveil.routes.Route
import com.group7.unveil.data.SelectedRouteFromHome
import com.group7.unveil.map.*
import com.group7.unveil.routes.RouteHeap
import kotlinx.android.synthetic.main.map_fragment.*

/**
 * Map page activity
 * @author M. Rose
 */
class Map : Fragment(), OnMapReadyCallback {
    private var mapHelper: LandmarkMap? = null
    private lateinit var map: GoogleMap
    private lateinit var locationManager: LocationManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //makes the map
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        createRouteButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().add(id, RouteCreation(mapHelper!!)).addToBackStack(null).commit()
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
        map.isMyLocationEnabled = LocationData.locationPermission
        map.uiSettings.isMyLocationButtonEnabled = LocationData.locationPermission
//        getLocation()

        //still need to add the route buttons so use the map centre
        if (!LocationData.locationPermission)
            mapHelper?.updateRouteHeap(Landmarks.centre)
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
