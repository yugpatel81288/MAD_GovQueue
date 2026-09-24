package com.example.madgovqueue

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_profile,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        val tvProfileName =
            view.findViewById<TextView>(R.id.tvProfileName)

        val tvProfileEmail =
            view.findViewById<TextView>(R.id.tvProfileEmail)

        val btnLogout =
            view.findViewById<MaterialButton>(R.id.btnLogout)

        val rowReports =
            view.findViewById<View>(R.id.rowProfileReports)

        val rowLanguage =
            view.findViewById<View>(R.id.rowProfileLanguage)

        val rowHelp =
            view.findViewById<View>(R.id.rowProfileHelp)

        val rowPrivacy =
            view.findViewById<View>(R.id.rowProfilePrivacy)


        // Get logged-in user
        val prefs = requireContext()
            .getSharedPreferences(
                "GovQueuePrefs",
                Context.MODE_PRIVATE
            )

        val savedEmail =
            prefs.getString("userEmail", "") ?: ""

        val databaseHelper =
            DatabaseHelper(requireContext())

        val userDetails =
            databaseHelper.getUserDetails(savedEmail)

        if (userDetails != null) {

            tvProfileName.text =
                userDetails.first

            tvProfileEmail.text =
                userDetails.second

        } else {

            tvProfileName.text =
                "GovQueue User"

            tvProfileEmail.text =
                savedEmail
        }


        // My Reports
        rowReports.setOnClickListener {

            val bottomNavigation =
                requireActivity()
                    .findViewById<BottomNavigationView>(
                        R.id.bottomNavigation
                    )

            bottomNavigation.selectedItemId =
                R.id.nav_reports
        }


        // Language
        rowLanguage.setOnClickListener {

            Toast.makeText(
                requireContext(),
                "English (India) is currently selected",
                Toast.LENGTH_SHORT
            ).show()
        }


        // Help & Support
        rowHelp.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Help & Support")
                .setMessage(
                    "GovQueue helps citizens check government office crowd levels and estimated waiting times before visiting."
                )
                .setPositiveButton(
                    "OK",
                    null
                )
                .show()
        }


        // Privacy Policy
        rowPrivacy.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Privacy Policy")
                .setMessage(
                    "GovQueue stores your account and crowd reports locally on this device. No personal information is sent to an external server in this version."
                )
                .setPositiveButton(
                    "OK",
                    null
                )
                .show()
        }


        // Logout
        btnLogout.setOnClickListener {

            showLogoutDialog()
        }
    }


    private fun showLogoutDialog() {

        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage(
                "Are you sure you want to logout from GovQueue?"
            )
            .setNegativeButton(
                "Cancel",
                null
            )
            .setPositiveButton(
                "Logout"
            ) { _, _ ->

                logoutUser()
            }
            .show()
    }


    private fun logoutUser() {

        val prefs = requireContext()
            .getSharedPreferences(
                "GovQueuePrefs",
                Context.MODE_PRIVATE
            )

        prefs.edit()
            .clear()
            .apply()

        val intent = Intent(
            requireContext(),
            LoginActivity::class.java
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

        requireActivity().finish()
    }
}