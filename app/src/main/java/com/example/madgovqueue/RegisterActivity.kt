package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.widget.EditText
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        databaseHelper = DatabaseHelper(this)

        val btnBack = findViewById<MaterialButton>(R.id.btnBack)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword =
            findViewById<EditText>(R.id.etConfirmPassword)

        val btnCreateAccount =
            findViewById<MaterialButton>(R.id.btnCreateAccount)

        val tvSignIn = findViewById<TextView>(R.id.tvSignIn)

        // Back button
        btnBack.setOnClickListener {
            finish()
        }

        // Sign In
        tvSignIn.setOnClickListener {
            finish()
        }

        // Create Account
        btnCreateAccount.setOnClickListener {

            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            // Check empty fields
            if (name.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Check password
            if (password != confirmPassword) {
                Toast.makeText(
                    this,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Save user
            val success = databaseHelper.registerUser(
                name,
                email,
                password
            )

            if (success) {

                Toast.makeText(
                    this,
                    "Account created successfully",
                    Toast.LENGTH_SHORT
                ).show()

                // Go to Login
                val intent = Intent(
                    this,
                    LoginActivity::class.java
                )

                startActivity(intent)
                finish()

            } else {

                Toast.makeText(
                    this,
                    "Email already registered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}