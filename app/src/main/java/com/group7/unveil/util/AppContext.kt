package com.group7.unveil.util

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.group7.unveil.MainPage
import com.group7.unveil.Map
import com.group7.unveil.R
import com.group7.unveil.UserPage
import kotlinx.coroutines.*

/**
 * Contains a static application context for usage in landmark strings
 * @author MR
 */
class AppContext : Application() {
    companion object {
        var locationPerms = false

        private lateinit var sApplication: Application

        fun getApplication() = sApplication

        fun getContext() = getApplication().applicationContext

        fun changeActivity(currentActivity: Int, it: MenuItem, ctx: Context) {
            if (currentActivity == it.itemId)
                return

            var intent: Intent? = null
            when (it.itemId) {
                R.id.nav_map -> intent = Intent(ctx, Map::class.java)
                R.id.nav_user -> intent = Intent(ctx, UserPage::class.java)
                R.id.nav_home -> intent = Intent(ctx, MainPage::class.java)
            }

            CoroutineScope(Dispatchers.Default).launch {
                delay(3)
                withContext(Dispatchers.IO) {
                    if (intent != null)
                        ctx.startActivity(intent)
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }
}
