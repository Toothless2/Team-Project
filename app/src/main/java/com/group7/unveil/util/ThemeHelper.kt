package com.group7.unveil.util

import android.app.Activity
import android.content.Intent
import com.group7.unveil.R

/**
 * @author Natalia
 */
object ThemeHelper {
    private var sTheme: Int = 0

    const val LightTheme = 0
    const val DarkTheme = 1
    const val Dyslexic = 2
    const val Small = 3
    const val Medium = 4
    const val Big = 5

    fun changeToTheme(activity: Activity, theme: Int) {
        sTheme = theme
        activity.finish()

        activity.startActivity(Intent(activity, activity::class.java))

    }

    fun onActivityCreateSetTheme(activity: Activity) {
        when (sTheme) {
            LightTheme -> activity.setTheme(R.style.LightTheme)
            DarkTheme -> activity.setTheme(R.style.DarkTheme)
            Dyslexic -> activity.setTheme(R.style.Dyslexic)
            Small -> activity.setTheme(R.style.FontSmall)
            Medium -> activity.setTheme(R.style.FontMedium)
            Big -> activity.setTheme(R.style.FontBig)
            else -> activity.setTheme(R.style.LightTheme)
        }

    }
}