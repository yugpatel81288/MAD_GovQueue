package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val btnNext = findViewById<MaterialButton>(R.id.btnNext)

        btnNext.setOnClickListener {

            val intent = Intent(this, Onboarding2Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}