package com.example.madgovqueue

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

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


        // Get logged-in user's email
        val prefs =
            requireContext().getSharedPreferences(
                "GovQueuePrefs",
                Context.MODE_PRIVATE
            )

        val savedEmail =
            prefs.getString(
                "userEmail",
                ""
            ) ?: ""


        // Get user details from SQLite
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


        // Logout
        btnLogout.setOnClickListener {

            prefs.edit()
                .clear()
                .apply()


            val intent =
                Intent(
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
}