package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class OfficeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_office_details)

        // Get office data from MainActivity
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


        // Find views
        val tvOfficeName =
            findViewById<TextView>(R.id.tvOfficeName)

        val tvService =
            findViewById<TextView>(R.id.tvService)

        val tvAddress =
            findViewById<TextView>(R.id.tvAddress)

        val tvDistance =
            findViewById<TextView>(R.id.tvDistance)

        val tvCrowd =
            findViewById<TextView>(R.id.tvCrowd)

        val tvWait =
            findViewById<TextView>(R.id.tvWait)

        val btnBack =
            findViewById<TextView>(R.id.btnBack)

        val btnReportCrowd =
            findViewById<MaterialButton>(R.id.btnReportCrowd)


        // Set office information
        tvOfficeName.text = officeName

        tvService.text = officeService

        tvAddress.text = "📍 $officeLocation"

        tvDistance.text = "📏 $officeDistance away"

        tvCrowd.text = officeCrowd.uppercase()

        tvWait.text = "Estimated wait: ~$officeWait"


        // Back button
        btnBack.setOnClickListener {
            finish()
        }


        // Report Crowd button
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