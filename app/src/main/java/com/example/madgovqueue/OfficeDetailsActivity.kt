package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class OfficeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_office_details)

        // Get data from previous screen
        val officeName =
            intent.getStringExtra("office_name") ?: "Government Office"

        val officeService =
            intent.getStringExtra("office_service") ?: "Government Services"

        val officeLocation =
            intent.getStringExtra("office_location") ?: "Ahmedabad, Gujarat"

        val officeDistance =
            intent.getStringExtra("office_distance") ?: "N/A"

        val officeCrowd =
            intent.getStringExtra("office_crowd") ?: "Unknown"

        val officeWait =
            intent.getStringExtra("office_wait") ?: "N/A"


        // Views
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        val tvOfficeName =
            findViewById<TextView>(R.id.tvDetailOfficeName)

        val tvOfficeService =
            findViewById<TextView>(R.id.tvDetailOfficeService)

        val tvAddress =
            findViewById<TextView>(R.id.tvDetailAddress)

        val tvHours =
            findViewById<TextView>(R.id.tvDetailHours)

        val badgeCrowd =
            findViewById<LinearLayout>(R.id.badgeDetailCrowd)

        val crowdDot =
            findViewById<ImageView>(R.id.ivDetailCrowdDot)

        val tvCrowdStatus =
            findViewById<TextView>(R.id.tvDetailCrowdStatus)

        val tvWaitTime =
            findViewById<TextView>(R.id.tvDetailWaitTime)

        val tvLastUpdated =
            findViewById<TextView>(R.id.tvDetailLastUpdated)

        val tvPeopleWaiting =
            findViewById<TextView>(R.id.tvDetailPeopleWaiting)

        val btnGetDirections =
            findViewById<MaterialButton>(R.id.btnGetDirections)

        val btnReportCrowd =
            findViewById<MaterialButton>(R.id.btnReportCrowd)


        // --------------------------------
        // Basic Office Information
        // --------------------------------

        tvOfficeName.text = officeName
        tvOfficeService.text = officeService

        tvAddress.text = officeLocation

        tvHours.text = "Mon - Fri: 9:30 AM – 5:30 PM"


        // --------------------------------
        // Crowd Status
        // --------------------------------

        when (officeCrowd) {

            "Low Crowd" -> {

                badgeCrowd.setBackgroundResource(
                    R.drawable.bg_badge_low
                )

                crowdDot.setImageResource(
                    R.drawable.ic_crowd_low
                )

                tvCrowdStatus.text = "LOW CROWD"

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

                tvCrowdStatus.text = "MODERATE CROWD"

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

                tvCrowdStatus.text = "HIGH CROWD"

                tvCrowdStatus.setTextColor(
                    getColor(R.color.crowd_high)
                )
            }

            else -> {

                tvCrowdStatus.text =
                    officeCrowd.uppercase()

                tvCrowdStatus.setTextColor(
                    getColor(R.color.gov_text_primary)
                )
            }
        }


        // --------------------------------
        // Estimated Waiting Time
        // --------------------------------

        tvWaitTime.text = officeWait


        // --------------------------------
        // Last Updated
        // --------------------------------

        tvLastUpdated.text = "Updated recently"


        // --------------------------------
        // People Waiting
        // --------------------------------
        //
        // Current Office model doesn't contain
        // people waiting, so we show a placeholder.
        //

        tvPeopleWaiting.text = "--"


        // --------------------------------
        // Back Button
        // --------------------------------

        toolbar.setNavigationOnClickListener {
            finish()
        }


        // --------------------------------
        // Get Directions
        // --------------------------------

        btnGetDirections.setOnClickListener {

            val destination = android.net.Uri.encode(officeLocation)

            val url =
                "https://www.google.com/maps/search/?api=1&query=$destination"

            val intent = Intent(
                Intent.ACTION_VIEW,
                android.net.Uri.parse(url)
            )

            try {
                startActivity(intent)
            } catch (e: Exception) {

                android.widget.Toast.makeText(
                    this,
                    "Please install a browser to open directions",
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }
        }


        // --------------------------------
        // Report Crowd
        // --------------------------------

        btnReportCrowd.setOnClickListener {

            val intent = Intent(
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
}