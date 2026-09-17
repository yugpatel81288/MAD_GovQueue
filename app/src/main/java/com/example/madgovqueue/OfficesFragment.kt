package com.example.madgovqueue

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OfficesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var tvNoResults: TextView

    private lateinit var adapter: OfficeAdapter

    private val allOffices = listOf(

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_offices,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText =
            view.findViewById(R.id.etSearchOffices)

        recyclerView =
            view.findViewById(R.id.rvOffices)

        tvNoResults =
            view.findViewById(R.id.tvNoResults)


        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())


        adapter = OfficeAdapter(
            offices = allOffices,
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


        // Search offices
        searchEditText.addTextChangedListener(
            object : android.text.TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    filterOffices(
                        s?.toString()?.trim() ?: ""
                    )
                }

                override fun afterTextChanged(
                    s: android.text.Editable?
                ) {
                }
            }
        )
    }


    private fun filterOffices(query: String) {

        if (query.isEmpty()) {

            recyclerView.visibility = View.VISIBLE
            tvNoResults.visibility = View.GONE

            adapter = OfficeAdapter(
                allOffices
            ) { office ->

                openOfficeDetails(office)
            }

            recyclerView.adapter = adapter

            return
        }


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


        if (filteredOffices.isEmpty()) {

            recyclerView.visibility = View.GONE
            tvNoResults.visibility = View.VISIBLE

        } else {

            recyclerView.visibility = View.VISIBLE
            tvNoResults.visibility = View.GONE

            adapter = OfficeAdapter(
                filteredOffices
            ) { office ->

                openOfficeDetails(office)
            }

            recyclerView.adapter = adapter
        }
    }


    private fun openOfficeDetails(
        office: Office
    ) {

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
}