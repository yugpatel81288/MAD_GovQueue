package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class Onboarding2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding2)

        val btnNext = findViewById<MaterialButton>(R.id.btnNext)

        btnNext.setOnClickListener {

            val intent = Intent(this, Onboarding3Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}