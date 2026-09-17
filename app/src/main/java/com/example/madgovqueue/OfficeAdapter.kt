package com.example.madgovqueue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class OfficeAdapter(
    private val offices: List<Office>,
    private val onViewDetailsClick: (Office) -> Unit
) : RecyclerView.Adapter<OfficeAdapter.OfficeViewHolder>() {

    class OfficeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val officeName: TextView =
            itemView.findViewById(R.id.tvOfficeName)

        val officeService: TextView =
            itemView.findViewById(R.id.tvOfficeService)

        val officeLocation: TextView =
            itemView.findViewById(R.id.tvOfficeLocation)

        val crowdBadge: LinearLayout =
            itemView.findViewById(R.id.badgeCrowdStatus)

        val crowdDot: ImageView =
            itemView.findViewById(R.id.ivCrowdDot)

        val crowdText: TextView =
            itemView.findViewById(R.id.tvCrowdStatusText)

        val viewDetailsButton: MaterialButton =
            itemView.findViewById(R.id.btnViewDetails)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfficeViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_office_card,
                parent,
                false
            )

        return OfficeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: OfficeViewHolder,
        position: Int
    ) {

        val office = offices[position]

        holder.officeName.text = office.name
        holder.officeService.text = office.service
        holder.officeLocation.text = office.location

        holder.crowdText.text =
            "${office.crowdStatus} • ${office.waitTime} wait"
        when (office.crowdStatus) {

            "Low Crowd" -> {
                holder.crowdBadge.setBackgroundResource(
                    R.drawable.bg_badge_low
                )
                holder.crowdDot.setImageResource(
                    R.drawable.ic_crowd_low
                )
                holder.crowdText.setTextColor(
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
                holder.crowdText.setTextColor(
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
                holder.crowdText.setTextColor(
                    holder.itemView.context.getColor(
                        R.color.crowd_high
                    )
                )
            }
        }

        holder.viewDetailsButton.setOnClickListener {
            onViewDetailsClick(office)
        }
    }

    override fun getItemCount(): Int {
        return offices.size
    }
}