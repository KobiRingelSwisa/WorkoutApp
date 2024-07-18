package com.mykotlinapps.bodybuilder

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class MuscleGroupPerformance(val muscleGroup: String, val performance: String, val insights: String)

class MuscleGroupAdapter(
    private val context: Context,
    private val muscleGroupList: List<MuscleGroupPerformance>
) : RecyclerView.Adapter<MuscleGroupAdapter.MuscleGroupViewHolder>() {

    class MuscleGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val muscleGroupTextView: TextView = itemView.findViewById(R.id.muscleGroupTextView)
        val performanceTextView: TextView = itemView.findViewById(R.id.performanceTextView)
        val insightsTextView: TextView = itemView.findViewById(R.id.insightsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuscleGroupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_muscle_group_card, parent, false)
        return MuscleGroupViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MuscleGroupViewHolder, position: Int) {
        val currentItem = muscleGroupList[position]
        holder.muscleGroupTextView.text = currentItem.muscleGroup
        holder.performanceTextView.text = currentItem.performance
        holder.insightsTextView.text = currentItem.insights

        holder.itemView.setOnClickListener {
            showDetailDialog(currentItem)
        }
    }

    override fun getItemCount() = muscleGroupList.size

    private fun showDetailDialog(muscleGroupPerformance: MuscleGroupPerformance) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_muscle_group_detail, null)
        val muscleGroupTextView: TextView = dialogView.findViewById(R.id.dialogMuscleGroupTextView)
        val performanceTextView: TextView = dialogView.findViewById(R.id.dialogPerformanceTextView)
        val insightsTextView: TextView = dialogView.findViewById(R.id.dialogInsightsTextView)

        muscleGroupTextView.text = muscleGroupPerformance.muscleGroup
        performanceTextView.text = muscleGroupPerformance.performance
        insightsTextView.text = muscleGroupPerformance.insights

        AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("OK", null)
            .show()
    }
}
