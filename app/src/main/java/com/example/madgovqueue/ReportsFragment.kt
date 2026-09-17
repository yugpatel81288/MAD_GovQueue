package com.example.madgovqueue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReportsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvNoReports: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_reports,
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
            view.findViewById(R.id.rvReports)

        tvNoReports =
            view.findViewById(R.id.tvNoReports)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        loadReports()
    }

    override fun onResume() {
        super.onResume()

        if (::recyclerView.isInitialized) {
            loadReports()
        }
    }

    private fun loadReports() {

        val databaseHelper =
            DatabaseHelper(requireContext())

        val reports =
            databaseHelper.getAllReports()

        if (reports.isEmpty()) {

            recyclerView.visibility = View.GONE
            tvNoReports.visibility = View.VISIBLE

        } else {

            recyclerView.visibility = View.VISIBLE
            tvNoReports.visibility = View.GONE

            val adapter =
                ReportAdapter(reports)

            recyclerView.adapter = adapter
        }
    }
}