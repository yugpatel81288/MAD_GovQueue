package com.example.madgovqueue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReportAdapter(
    private val reports: List<Report>
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val officeName: TextView =
            itemView.findViewById(R.id.tvReportOfficeName)

        val crowdBadge: LinearLayout =
            itemView.findViewById(R.id.badgeReportCrowd)

        val crowdDot: ImageView =
            itemView.findViewById(R.id.ivReportCrowdDot)

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

        val view = LayoutInflater.from(parent.context).inflate(
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

        // Office name
        holder.officeName.text = report.officeName

        // Crowd status
        when (report.crowdStatus) {

            "Low Crowd" -> {

                holder.crowdBadge.setBackgroundResource(
                    R.drawable.bg_badge_low
                )

                holder.crowdDot.setImageResource(
                    R.drawable.ic_crowd_low
                )

                holder.crowdStatus.text = "LOW CROWD"

                holder.crowdStatus.setTextColor(
                    holder.itemView.context.getColor(
                        R.color.crowd_low
                    )
                )
            }

            "Moderate Crowd" -> {

                holder.crowdBadge.setBackgroundResource(
                    R.drawable.bg_badge_moderate
                )

                holder.crowdDot.setImageResource(
                    R.drawable.ic_crowd_moderate
                )

                holder.crowdStatus.text = "MODERATE CROWD"

                holder.crowdStatus.setTextColor(
                    holder.itemView.context.getColor(
                        R.color.crowd_moderate
                    )
                )
            }

            "High Crowd" -> {

                holder.crowdBadge.setBackgroundResource(
                    R.drawable.bg_badge_high
                )

                holder.crowdDot.setImageResource(
                    R.drawable.ic_crowd_high
                )

                holder.crowdStatus.text = "HIGH CROWD"

                holder.crowdStatus.setTextColor(
                    holder.itemView.context.getColor(
                        R.color.crowd_high
                    )
                )
            }

            else -> {

                holder.crowdStatus.text =
                    report.crowdStatus.uppercase()

                holder.crowdStatus.setTextColor(
                    holder.itemView.context.getColor(
                        R.color.gov_text_primary
                    )
                )
            }
        }

        // People waiting
        holder.peopleWaiting.text =
            if (report.peopleWaiting.isEmpty()) {
                "People waiting: Not provided"
            } else {
                "People waiting: ${report.peopleWaiting}"
            }

        // Report time
        holder.reportTime.text =
            formatTime(report.reportTime)
    }

    override fun getItemCount(): Int {
        return reports.size
    }

    private fun formatTime(time: String): String {

        return try {

            val timestamp = time.toLong()

            val date = java.text.SimpleDateFormat(
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