package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var tvNoHomeResults: TextView
    private lateinit var adapter: OfficeAdapter

    private val allOffices = listOf(

        Office(
            "Regional Passport Office",
            "Passport Services",
            "Ahmedabad, Gujarat",
            "1.2 km",
            "Moderate Crowd",
            "35 min"
        ),

        Office(
            "Ahmedabad Municipal Corporation",
            "Birth / Death Certificate, Tax",
            "Ahmedabad, Gujarat",
            "2.5 km",
            "High Crowd",
            "65 min"
        ),

        Office(
            "Income Tax Office – Navrangpura",
            "PAN, TDS, ITR",
            "Navrangpura, Ahmedabad",
            "3.8 km",
            "Low Crowd",
            "12 min"
        ),

        Office(
            "RTO Ahmedabad West",
            "Driving License, RC, NOC",
            "Ahmedabad, Gujarat",
            "4.1 km",
            "High Crowd",
            "80 min"
        ),

        Office(
            "District Collectorate Office",
            "Caste, Domicile, OBC Certificates",
            "Ahmedabad, Gujarat",
            "5.3 km",
            "Low Crowd",
            "15 min"
        )
    )

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

        recyclerView =
            view.findViewById(R.id.rvNearbyOffices)

        searchEditText =
            view.findViewById(R.id.etSearchHome)

        tvNoHomeResults =
            view.findViewById(R.id.tvNoHomeResults)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        // Show all offices initially
        setupAdapter(allOffices)

        // Setup search
        setupSearch()
    }

    private fun setupAdapter(offices: List<Office>) {

        adapter = OfficeAdapter(offices) { office ->

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

        recyclerView.adapter = adapter
    }

    private fun setupSearch() {

        searchEditText.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Not required
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    val query =
                        s?.toString()?.trim() ?: ""

                    filterOffices(query)
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                    // Not required
                }
            }
        )
    }

    private fun filterOffices(query: String) {

        // Empty search
        if (query.isEmpty()) {

            recyclerView.visibility =
                View.VISIBLE

            tvNoHomeResults.visibility =
                View.GONE

            setupAdapter(allOffices)

            return
        }

        // Filter offices
        val filteredOffices =
            allOffices.filter { office ->

                office.name.contains(
                    query,
                    ignoreCase = true
                ) ||

                        office.service.contains(
                            query,
                            ignoreCase = true
                        ) ||

                        office.location.contains(
                            query,
                            ignoreCase = true
                        )
            }

        // No results
        if (filteredOffices.isEmpty()) {

            recyclerView.visibility =
                View.GONE

            tvNoHomeResults.visibility =
                View.VISIBLE

        } else {

            // Results found
            recyclerView.visibility =
                View.VISIBLE

            tvNoHomeResults.visibility =
                View.GONE

            setupAdapter(filteredOffices)
        }
    }
}