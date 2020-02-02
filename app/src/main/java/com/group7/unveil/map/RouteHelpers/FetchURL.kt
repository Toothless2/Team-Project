//TODO: comment

package com.group7.unveil.map.RouteHelpers

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class FetchURL(val ctx: Context) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg strings: String): String {
        var data = ""
        try {
            data = downloadURL(strings[0])
        } catch (e: Exception) {
            Log.d("URL Backgroud Task", e.toString())
        }

        return data
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        parser = PointsParser(ctx, "walking")
        parser.execute(result)
    }

    private fun downloadURL(url: String): String {
        var data = ""
        val iStream: InputStream
        val urlConnection: HttpURLConnection

        try {
            val url = URL(url)
            urlConnection = url.openConnection() as HttpURLConnection
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