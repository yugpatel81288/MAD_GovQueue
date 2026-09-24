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

        val etEmail =
            findViewById<EditText>(R.id.etEmail)

        val etPassword =
            findViewById<EditText>(R.id.etPassword)

        val btnSignIn =
            findViewById<MaterialButton>(R.id.btnSignIn)

        val btnCreateAccount =
            findViewById<MaterialButton>(R.id.btnCreateAccount)

        val tvForgotPassword =
            findViewById<TextView>(R.id.tvForgotPassword)


        // --------------------------------
        // Sign In
        // --------------------------------

        btnSignIn.setOnClickListener {

            val email =
                etEmail.text.toString().trim()

            val password =
                etPassword.text.toString().trim()

            if (email.isEmpty()) {

                etEmail.error =
                    "Enter your email"

                etEmail.requestFocus()

                return@setOnClickListener
            }

            if (password.isEmpty()) {

                etPassword.error =
                    "Enter your password"

                etPassword.requestFocus()

                return@setOnClickListener
            }

            val loginSuccessful =
                databaseHelper.loginUser(
                    email,
                    password
                )

            if (loginSuccessful) {

                val prefs =
                    getSharedPreferences(
                        "GovQueuePrefs",
                        MODE_PRIVATE
                    )

                prefs.edit()
                    .putBoolean(
                        "isLoggedIn",
                        true
                    )
                    .putString(
                        "userEmail",
                        email
                    )
                    .apply()

                Toast.makeText(
                    this,
                    "Login successful",
                    Toast.LENGTH_SHORT
                ).show()

                val intent =
                    Intent(
                        this,
                        MainActivity::class.java
                    )

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

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


        // --------------------------------
        // Create Account
        // --------------------------------

        btnCreateAccount.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }


        // --------------------------------
        // Forgot Password
        // --------------------------------

        tvForgotPassword.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ForgotPasswordActivity::class.java
                )
            )
        }
    }
}