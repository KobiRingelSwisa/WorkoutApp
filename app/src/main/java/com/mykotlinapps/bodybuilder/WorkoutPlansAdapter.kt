package com.mykotlinapps.bodybuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.databinding.ItemWorkoutPlanBinding

class WorkoutPlansAdapter(private val workoutPlans: List<String>) : RecyclerView.Adapter<WorkoutPlansAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemWorkoutPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workoutName: String) {
            binding.workoutName.text = workoutName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkoutPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(workoutPlans[position])
    }

    override fun getItemCount() = workoutPlans.size
}