package com.group7.unveil

import android.app.Activity
import android.content.Intent


internal object Utils {
    private var sTheme: Int = 0

    val LightTheme = 0
    val DarkTheme = 1
    val Dyslexic = 2
    val Small = 3
    val Medium = 4
    val Big = 5


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