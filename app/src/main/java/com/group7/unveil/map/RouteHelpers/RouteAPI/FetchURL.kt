package com.group7.unveil.map.RouteHelpers.RouteAPI

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * Helper class to get map route information
 * @author Max Rose
 */
class FetchURL(val callback: TaskLoadedCallback) : AsyncTask<String, Void, String>() {

    /**
     * Gets the data from the URL in the background
     */
    override fun doInBackground(vararg strings: String): String {
        var data = ""
        try {
            data = downloadURL(strings[0])
        } catch (e: Exception) {
            Log.w("URL Background Task Exception", e.toString())
        }

        return data
    }

    /**
     * Parses the URL data in the background for it to be returned
     */
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        val parser = PointsParser(callback, "walking")
        parser.execute(result)
    }

    /**
     * Downloads data from the given URL
     */
    private fun downloadURL(url: String): String {
        var data = ""
        val iStream: InputStream
        val urlConnection: HttpURLConnection

        try {
            val _url = URL(url)
            urlConnection = _url.openConnection() as HttpURLConnection
            urlConnection.connect()
            iStream = urlConnection.inputStream

            val br = BufferedReader(InputStreamReader(iStream))
            val stringBuffer = StringBuffer()

            br.forEachLine { x -> stringBuffer.append(x) }
            iStream.close()
            urlConnection.disconnect()

            data = stringBuffer.toString()
            br.close()
        } catch (e: Exception) {
            Log.d("Download URL Exception", e.toString())
        }

        return data
    }
}