package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            val sharedPreferences = getSharedPreferences(
                "GovQueuePrefs",
                MODE_PRIVATE
            )

            val isLoggedIn = sharedPreferences.getBoolean(
                "isLoggedIn",
                false
            )

            if (isLoggedIn) {

                startActivity(
                    Intent(this, MainActivity::class.java)
                )

            } else {

                startActivity(
                    Intent(this, OnboardingActivity::class.java)
                )
            }

            finish()

        }, 2000)
    }
}