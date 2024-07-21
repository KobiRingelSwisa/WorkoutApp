package com.mykotlinapps.bodybuilder.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.data.MuscleGroupPerformance
import com.mykotlinapps.bodybuilder.databinding.MuscleGroupAnalyticsButtonBinding
import com.mykotlinapps.bodybuilder.ui.fragments.MuscleGroupDetailBottomSheetFragment

class MuscleGroupAdapter(
    private val muscleGroupList: List<MuscleGroupPerformance>,
    private val onMuscleGroupClick: (MuscleGroupPerformance) -> Unit
) : RecyclerView.Adapter<MuscleGroupAdapter.MuscleGroupViewHolder>() {

    inner class MuscleGroupViewHolder(private val binding: MuscleGroupAnalyticsButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(muscleGroupPerformance: MuscleGroupPerformance) {
            binding.muscleGroupButton.text = muscleGroupPerformance.muscleGroup
            binding.performanceTextView.text = muscleGroupPerformance.performance
            binding.insightsTextView.text = muscleGroupPerformance.insights

            binding.muscleGroupButton.setOnClickListener {
                onMuscleGroupClick(muscleGroupPerformance)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuscleGroupViewHolder {
        val binding = MuscleGroupAnalyticsButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MuscleGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MuscleGroupViewHolder, position: Int) {
        holder.bind(muscleGroupList[position])
    }

    override fun getItemCount() = muscleGroupList.size
}
