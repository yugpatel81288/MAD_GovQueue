package com.example.madgovqueue

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class OfficeDetailsActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var officeName: String
    private lateinit var officeLocation: String

    private var defaultCrowd = "Unknown"
    private var defaultWait = "N/A"

    private lateinit var tvOfficeName: TextView
    private lateinit var tvOfficeService: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvHours: TextView
    private lateinit var badgeCrowd: LinearLayout
    private lateinit var crowdDot: ImageView
    private lateinit var tvCrowdStatus: TextView
    private lateinit var tvWaitTime: TextView
    private lateinit var tvLastUpdated: TextView
    private lateinit var tvPeopleWaiting: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_office_details)

        databaseHelper = DatabaseHelper(this)

        // --------------------------------
        // Get office information
        // --------------------------------

        officeName =
            intent.getStringExtra("office_name")
                ?: "Government Office"

        val officeService =
            intent.getStringExtra("office_service")
                ?: "Government Services"

        officeLocation =
            intent.getStringExtra("office_location")
                ?: "Ahmedabad, Gujarat"

        val officeDistance =
            intent.getStringExtra("office_distance")
                ?: "N/A"

        defaultCrowd =
            intent.getStringExtra("office_crowd")
                ?: "Unknown"

        defaultWait =
            intent.getStringExtra("office_wait")
                ?: "N/A"


        // --------------------------------
        // Find views
        // --------------------------------

        val toolbar =
            findViewById<MaterialToolbar>(R.id.toolbar)

        tvOfficeName =
            findViewById(R.id.tvDetailOfficeName)

        tvOfficeService =
            findViewById(R.id.tvDetailOfficeService)

        tvAddress =
            findViewById(R.id.tvDetailAddress)

        tvHours =
            findViewById(R.id.tvDetailHours)

        badgeCrowd =
            findViewById(R.id.badgeDetailCrowd)

        crowdDot =
            findViewById(R.id.ivDetailCrowdDot)

        tvCrowdStatus =
            findViewById(R.id.tvDetailCrowdStatus)

        tvWaitTime =
            findViewById(R.id.tvDetailWaitTime)

        tvLastUpdated =
            findViewById(R.id.tvDetailLastUpdated)

        tvPeopleWaiting =
            findViewById(R.id.tvDetailPeopleWaiting)

        val btnGetDirections =
            findViewById<MaterialButton>(
                R.id.btnGetDirections
            )

        val btnReportCrowd =
            findViewById<MaterialButton>(
                R.id.btnReportCrowd
            )


        // --------------------------------
        // Static office information
        // --------------------------------

        tvOfficeName.text = officeName

        tvOfficeService.text = officeService

        tvAddress.text = officeLocation

        tvHours.text =
            "Mon - Fri: 9:30 AM – 5:30 PM"


        // --------------------------------
        // Back button
        // --------------------------------

        toolbar.setNavigationOnClickListener {
            finish()
        }


        // --------------------------------
        // Get Directions
        // --------------------------------

        btnGetDirections.setOnClickListener {

            val destination =
                Uri.encode(officeLocation)

            val url =
                "https://www.google.com/maps/search/?api=1&query=$destination"

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )

            try {

                startActivity(intent)

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Unable to open directions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        // --------------------------------
        // Report Crowd
        // --------------------------------

        btnReportCrowd.setOnClickListener {

            val intent =
                Intent(
                    this,
                    ReportCrowdActivity::class.java
                )

            intent.putExtra(
                "office_name",
                officeName
            )

            startActivity(intent)
        }
    }


    // ================================================
    // THIS RUNS EVERY TIME YOU RETURN TO OFFICE DETAILS
    // ================================================

    override fun onResume() {

        super.onResume()

        loadLatestCrowdReport()
    }


    // ================================================
    // LOAD LATEST REPORT
    // ================================================

    private fun loadLatestCrowdReport() {

        val latestReport =
            databaseHelper.getLatestReportForOffice(
                officeName
            )

        if (latestReport != null) {

            // -------------------------------
            // Latest reported crowd
            // -------------------------------

            applyCrowdStatus(
                latestReport.crowdStatus
            )


            // -------------------------------
            // People waiting
            // -------------------------------

            if (latestReport.peopleWaiting.isNotEmpty()) {

                tvPeopleWaiting.text =
                    latestReport.peopleWaiting

            } else {

                tvPeopleWaiting.text =
                    "--"
            }


            // -------------------------------
            // Calculate wait
            // -------------------------------

            tvWaitTime.text =
                calculateWaitTime(
                    latestReport.crowdStatus,
                    latestReport.peopleWaiting
                )


            // -------------------------------
            // Updated time
            // -------------------------------

            tvLastUpdated.text =
                "Updated just now"

        } else {

            // No report yet
            applyCrowdStatus(
                defaultCrowd
            )

            tvWaitTime.text =
                defaultWait

            tvPeopleWaiting.text =
                "--"

            tvLastUpdated.text =
                "No recent citizen report"
        }
    }


    // ================================================
    // APPLY CROWD STATUS UI
    // ================================================

    private fun applyCrowdStatus(
        crowdStatus: String
    ) {

        when (crowdStatus) {

            "Low Crowd" -> {

                badgeCrowd.setBackgroundResource(
                    R.drawable.bg_badge_low
                )

                crowdDot.setImageResource(
                    R.drawable.ic_crowd_low
                )

                tvCrowdStatus.text =
                    "LOW CROWD"

                tvCrowdStatus.setTextColor(
                    getColor(R.color.crowd_low)
                )
            }


            "Moderate Crowd" -> {

                badgeCrowd.setBackgroundResource(
                    R.drawable.bg_badge_moderate
                )

                crowdDot.setImageResource(
                    R.drawable.ic_crowd_moderate
                )

                tvCrowdStatus.text =
                    "MODERATE CROWD"

                tvCrowdStatus.setTextColor(
                    getColor(R.color.crowd_moderate)
                )
            }


            "High Crowd" -> {

                badgeCrowd.setBackgroundResource(
                    R.drawable.bg_badge_high
                )

                crowdDot.setImageResource(
                    R.drawable.ic_crowd_high
                )

                tvCrowdStatus.text =
                    "HIGH CROWD"

                tvCrowdStatus.setTextColor(
                    getColor(R.color.crowd_high)
                )
            }


            else -> {

                tvCrowdStatus.text =
                    crowdStatus.uppercase()

                tvCrowdStatus.setTextColor(
                    getColor(R.color.gov_text_primary)
                )
            }
        }
    }


    // ================================================
    // WAIT TIME CALCULATION
    // ================================================

    private fun calculateWaitTime(
        crowdStatus: String,
        peopleWaiting: String
    ): String {

        val people =
            peopleWaiting.toIntOrNull()

        if (people != null) {

            return when {

                people <= 5 ->
                    "10 minutes"

                people <= 10 ->
                    "20 minutes"

                people <= 20 ->
                    "35 minutes"

                people <= 30 ->
                    "50 minutes"

                else ->
                    "65 minutes"
            }
        }

        return when (crowdStatus) {

            "Low Crowd" ->
                "15 minutes"

            "Moderate Crowd" ->
                "35 minutes"

            "High Crowd" ->
                "60 minutes"

            else ->
                "N/A"
        }
    }
}