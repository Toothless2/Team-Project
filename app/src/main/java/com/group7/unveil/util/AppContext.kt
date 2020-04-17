package com.group7.unveil.util

import android.app.Application
import android.content.Context
import android.util.Log

/**
 * Exposes a public getter for the current application context
 * @author M. Rose
 */
class AppContext : Application() {
    companion object{
        lateinit var appContext : Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("test", "hi")
        appContext = this.applicationContext
    }
}