package com.group7.unveil.util

import android.app.Application
import android.content.Context
import android.util.Log

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