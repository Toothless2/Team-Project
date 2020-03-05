package com.group7.unveil

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_navigation.*

class Navigation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )

        bottom_navigation.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(R.id.fragmetHolder, MainPage()).commit()
    }

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.nav_home -> fragment = MainPage()
                R.id.nav_user -> fragment = UserPage()
                R.id.nav_map -> fragment = Map()
            }

            if (fragment != null)
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragmetHolder,
                    fragment
                ).commit()

            true
        }
}
