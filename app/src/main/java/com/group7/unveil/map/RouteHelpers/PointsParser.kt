package com.group7.unveil.map.RouteHelpers

import android.content.ComponentCallbacks
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject

class PointsParser(val ctx: Context, val directionMode: String) :
    AsyncTask<String, Int, List<List<HashMap<String, String>>>>() {
    val taskCallback = ctx as TaskLoadedCallback

    override fun doInBackground(vararg jsonData: String): List<List<HashMap<String, String>>> {
        var jObject: JSONObject
        val routes: List<List<HashMap<String, String>>>

        try {
            jObject = JSONObject(jsonData[0])
            Log.d()
        }
    }
}