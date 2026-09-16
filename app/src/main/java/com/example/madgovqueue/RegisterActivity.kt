package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val btnBack = findViewById<MaterialButton>(R.id.btnBack)
        val tvSignIn = findViewById<android.widget.TextView>(R.id.tvSignIn)

        btnBack.setOnClickListener {
            finish()
        }

        tvSignIn.setOnClickListener {
            finish()
        }
    }
}