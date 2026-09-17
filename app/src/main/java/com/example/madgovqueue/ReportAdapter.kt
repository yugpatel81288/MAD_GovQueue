package com.example.madgovqueue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReportAdapter(
    private val reports: List<Report>
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val officeName: TextView =
            itemView.findViewById(R.id.tvReportOfficeName)

        val crowdStatus: TextView =
            itemView.findViewById(R.id.tvReportCrowd)

        val peopleWaiting: TextView =
            itemView.findViewById(R.id.tvReportPeople)

        val reportTime: TextView =
            itemView.findViewById(R.id.tvReportTime)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_report_card,
                parent,
                false
            )

        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ReportViewHolder,
        position: Int
    ) {

        val report = reports[position]

        holder.officeName.text =
            report.officeName

        holder.crowdStatus.text =
            report.crowdStatus

        if (report.peopleWaiting.isEmpty()) {

            holder.peopleWaiting.text =
                "People waiting: Not provided"

        } else {

            holder.peopleWaiting.text =
                "People waiting: ${report.peopleWaiting}"
        }

        holder.reportTime.text =
            formatTime(report.reportTime)
    }

    override fun getItemCount(): Int =
        reports.size

    private fun formatTime(time: String): String {

        return try {

            val timestamp =
                time.toLong()

            val date =
                java.text.SimpleDateFormat(
                    "dd MMM yyyy, hh:mm a",
                    java.util.Locale.getDefault()
                ).format(
                    java.util.Date(timestamp)
                )

            "Reported: $date"

        } catch (e: Exception) {

            "Reported: Recently"
        }
    }
}