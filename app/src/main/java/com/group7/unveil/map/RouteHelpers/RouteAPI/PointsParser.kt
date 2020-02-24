package com.group7.unveil.map.RouteHelpers.RouteAPI

import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONObject

/**
 * Helper class to convert the data retrieved by google maps routes URL from a JSON file to list of routes containing a list of points
 * @author Max Rose
 */
class PointsParser(callback: TaskLoadedCallback, val directionMode: String) :
    AsyncTask<String, Int, List<List<HashMap<String, String>>>>() {
    val taskCallback = callback

    /**
     * Given JSON route data as a string it will convert it into usable data
     * @return List of routes containing a List of points
     */
    override fun doInBackground(vararg jsonData: String): List<List<HashMap<String, String>>> {
        val jObject: JSONObject
        val routes: List<List<HashMap<String, String>>>

        try {
            jObject = JSONObject(jsonData[0])
            Log.d("JSON Map Data", jsonData[0])
            val parser = DataParser()
            Log.d("JSON Map Data", parser.toString())

            routes = parser.parse(jObject)

            return routes
        } catch (e: Exception) {
            Log.d("Error parsing JSON", e.toString())
            e.printStackTrace()
        }

        return emptyList()
    }

    /**
     * Puts the routes onto the map
     */
    override fun onPostExecute(result: List<List<HashMap<String, String>>>) {
        if (result == emptyList<List<HashMap<String, String>>>())
            return

        var points: ArrayList<LatLng?>
        var lineOptions: PolylineOptions? = null
        // Traversing through all the routes
        for (i in result.indices) {
            points = ArrayList()
            lineOptions = PolylineOptions()
            // Fetching i-th route
            val path = result[i]
            // Fetching all the points in i-th route
            for (j in path.indices) {
                val point = path[j]
                val lat = point["lat"]!!.toDouble()
                val lng = point["lng"]!!.toDouble()
                val position = LatLng(lat, lng)
                points.add(position)
            }
            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points)
            if (directionMode.equals("walking", ignoreCase = true)) {
                lineOptions.width(10f)
                lineOptions.color(Color.MAGENTA)
            } else {
                lineOptions.width(20f)
                lineOptions.color(Color.BLUE)
            }
            Log.d("Line Creation", "onPostExecute lineoptions decoded")
        }
        // Drawing polyline in the Google Map for the i-th route
        if (lineOptions != null) { //mMap.addPolyline(lineOptions);
            taskCallback.onTaskDone(lineOptions)
        } else {
            Log.d("Line Creation", "without Polylines drawn")
        }
    }
}