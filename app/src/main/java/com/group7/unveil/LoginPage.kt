package com.group7.unveil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, Navigation::class.java))

        finish()
    }


}
