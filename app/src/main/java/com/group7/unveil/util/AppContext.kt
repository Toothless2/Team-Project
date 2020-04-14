package com.group7.unveil.util

import android.app.Application
import android.content.Context

class AppContext : Application() {
    companion object{
        lateinit var appContext : Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
    }
}