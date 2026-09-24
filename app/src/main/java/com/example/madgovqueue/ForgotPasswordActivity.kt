package com.example.madgovqueue

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_forgot_password
        )

        databaseHelper =
            DatabaseHelper(this)

        val toolbar =
            findViewById<MaterialToolbar>(
                R.id.toolbar
            )

        val etEmail =
            findViewById<EditText>(
                R.id.etForgotEmail
            )

        val etNewPassword =
            findViewById<EditText>(
                R.id.etNewPassword
            )

        val etConfirmPassword =
            findViewById<EditText>(
                R.id.etConfirmPassword
            )

        val btnResetPassword =
            findViewById<MaterialButton>(
                R.id.btnResetPassword
            )


        // Back
        toolbar.setNavigationOnClickListener {
            finish()
        }


        // Reset Password
        btnResetPassword.setOnClickListener {

            val email =
                etEmail.text.toString().trim()

            val newPassword =
                etNewPassword.text.toString().trim()

            val confirmPassword =
                etConfirmPassword.text.toString().trim()


            if (email.isEmpty()) {

                etEmail.error =
                    "Enter your registered email"

                etEmail.requestFocus()

                return@setOnClickListener
            }


            if (newPassword.isEmpty()) {

                etNewPassword.error =
                    "Enter a new password"

                etNewPassword.requestFocus()

                return@setOnClickListener
            }


            if (newPassword.length < 6) {

                etNewPassword.error =
                    "Password must be at least 6 characters"

                etNewPassword.requestFocus()

                return@setOnClickListener
            }


            if (confirmPassword.isEmpty()) {

                etConfirmPassword.error =
                    "Confirm your password"

                etConfirmPassword.requestFocus()

                return@setOnClickListener
            }


            if (newPassword != confirmPassword) {

                etConfirmPassword.error =
                    "Passwords do not match"

                etConfirmPassword.requestFocus()

                return@setOnClickListener
            }


            val updated =
                databaseHelper.resetPassword(
                    email,
                    newPassword
                )


            if (updated) {

                Toast.makeText(
                    this,
                    "Password reset successfully",
                    Toast.LENGTH_LONG
                ).show()

                finish()

            } else {

                Toast.makeText(
                    this,
                    "No account found with this email",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}