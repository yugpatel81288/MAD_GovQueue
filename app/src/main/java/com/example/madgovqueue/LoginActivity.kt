package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        databaseHelper = DatabaseHelper(this)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnSignIn =
            findViewById<MaterialButton>(R.id.btnSignIn)

        val btnCreateAccount =
            findViewById<MaterialButton>(R.id.btnCreateAccount)

        // Sign In
        btnSignIn.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val loginSuccessful =
                databaseHelper.loginUser(email, password)

            if (loginSuccessful) {

                val sharedPreferences = getSharedPreferences(
                    "GovQueuePrefs",
                    MODE_PRIVATE
                )

                sharedPreferences.edit()
                    .putBoolean("isLoggedIn", true)
                    .apply()

                Toast.makeText(
                    this,
                    "Login successful",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(
                    this,
                    MainActivity::class.java
                )

                startActivity(intent)
                finish()
            } else {

                Toast.makeText(
                    this,
                    "Invalid email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Create Account
        btnCreateAccount.setOnClickListener {

            val intent = Intent(
                this,
                RegisterActivity::class.java
            )

            startActivity(intent)
        }
    }
}