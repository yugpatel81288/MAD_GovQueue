package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView =
            view.findViewById<RecyclerView>(
                R.id.rvNearbyOffices
            )

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())


        // Government offices
        val offices = listOf(

            Office(
                name = "Regional Passport Office",
                service = "Passport Services",
                location = "Ahmedabad, Gujarat",
                distance = "1.2 km",
                crowdStatus = "Moderate Crowd",
                waitTime = "35 min"
            ),

            Office(
                name = "Ahmedabad Municipal Corporation",
                service = "Birth / Death Certificate, Tax",
                location = "Ahmedabad, Gujarat",
                distance = "2.5 km",
                crowdStatus = "High Crowd",
                waitTime = "65 min"
            ),

            Office(
                name = "Income Tax Office – Navrangpura",
                service = "PAN, TDS, ITR",
                location = "Navrangpura, Ahmedabad",
                distance = "3.8 km",
                crowdStatus = "Low Crowd",
                waitTime = "12 min"
            ),

            Office(
                name = "RTO Ahmedabad West",
                service = "Driving License, RC, NOC",
                location = "Ahmedabad, Gujarat",
                distance = "4.1 km",
                crowdStatus = "High Crowd",
                waitTime = "80 min"
            ),

            Office(
                name = "District Collectorate Office",
                service = "Caste, Domicile, OBC Certificates",
                location = "Ahmedabad, Gujarat",
                distance = "5.3 km",
                crowdStatus = "Low Crowd",
                waitTime = "15 min"
            )
        )


        val adapter = OfficeAdapter(
            offices = offices,

            onViewDetailsClick = { office ->

                val intent = Intent(
                    requireContext(),
                    OfficeDetailsActivity::class.java
                )

                intent.putExtra(
                    "office_name",
                    office.name
                )

                intent.putExtra(
                    "office_service",
                    office.service
                )

                intent.putExtra(
                    "office_location",
                    office.location
                )

                intent.putExtra(
                    "office_distance",
                    office.distance
                )

                intent.putExtra(
                    "office_crowd",
                    office.crowdStatus
                )

                intent.putExtra(
                    "office_wait",
                    office.waitTime
                )

                startActivity(intent)
            }
        )


        recyclerView.adapter = adapter
    }
}