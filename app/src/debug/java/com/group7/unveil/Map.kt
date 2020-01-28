//TODO: comment

package com.group7.unveil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.group7.unveil.map.LandmarkMap


class Map : AppCompatActivity() {

    private lateinit var mMap: GoogleMap

    private lateinit var map: LandmarkMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        map = LandmarkMap(findViewById(R.id.mapView), savedInstanceState, applicationContext)

        val listV = findViewById<ListView>(R.id.listView)
        listV.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            arrayOf("Copy", "Paste", "Cut", "Delete", "Convert", "Open")
        )
    }
}
