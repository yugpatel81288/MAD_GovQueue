package com.example.madgovqueue

import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ReportCrowdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_report_crowd)

        // Get office name from OfficeDetailsActivity
        val officeName = intent.getStringExtra("office_name")
            ?: "Government Office"


        // Find views
        val toolbar =
            findViewById<MaterialToolbar>(R.id.toolbar)

        val tvReportOfficeName =
            findViewById<TextView>(R.id.tvReportOfficeName)

        val rbLow =
            findViewById<RadioButton>(R.id.rbLow)

        val rbModerate =
            findViewById<RadioButton>(R.id.rbModerate)

        val rbHigh =
            findViewById<RadioButton>(R.id.rbHigh)

        val etPeopleWaiting =
            findViewById<TextInputEditText>(R.id.etPeopleWaiting)

        val btnSubmitReport =
            findViewById<MaterialButton>(R.id.btnSubmitReport)


        // Show office name
        tvReportOfficeName.text = officeName


        // Toolbar back button
        toolbar.setNavigationOnClickListener {
            finish()
        }


        // Low Crowd card
        findViewById<android.view.View>(R.id.cardOptionLow)
            .setOnClickListener {

                rbLow.isChecked = true
                rbModerate.isChecked = false
                rbHigh.isChecked = false
            }


        // Moderate Crowd card
        findViewById<android.view.View>(R.id.cardOptionModerate)
            .setOnClickListener {

                rbLow.isChecked = false
                rbModerate.isChecked = true
                rbHigh.isChecked = false
            }


        // High Crowd card
        findViewById<android.view.View>(R.id.cardOptionHigh)
            .setOnClickListener {

                rbLow.isChecked = false
                rbModerate.isChecked = false
                rbHigh.isChecked = true
            }


        // Submit report
        btnSubmitReport.setOnClickListener {

            // Check selected crowd level
            val crowdLevel = when {

                rbLow.isChecked ->
                    "Low Crowd"

                rbModerate.isChecked ->
                    "Moderate Crowd"

                rbHigh.isChecked ->
                    "High Crowd"

                else ->
                    null
            }


            // If no option selected
            if (crowdLevel == null) {

                Toast.makeText(
                    this,
                    "Please select a crowd level",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }


            // Get optional number of people waiting
            val peopleWaiting =
                etPeopleWaiting.text
                    ?.toString()
                    ?.trim()
                    ?: ""


            // Create database helper
            val databaseHelper =
                DatabaseHelper(this)


            // Save report to SQLite
            val saved = databaseHelper.addCrowdReport(
                officeName = officeName,
                crowdStatus = crowdLevel,
                peopleWaiting = peopleWaiting
            )


            // Check whether report was saved
            if (saved) {

                Toast.makeText(
                    this,
                    "Crowd report submitted successfully",
                    Toast.LENGTH_LONG
                ).show()

                // Return to Office Details
                finish()

            } else {

                Toast.makeText(
                    this,
                    "Failed to submit report",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}