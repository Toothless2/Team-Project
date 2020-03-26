package com.group7.unveil.util

import android.app.Application

/**
 * Contains a static application context for usage in landmark strings
 * @author MR
 */
class AppContext : Application() {
    companion object {
        var locationPerms = false

        lateinit var sApplication: Application
            private set

        fun getContext() = sApplication.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }
}
