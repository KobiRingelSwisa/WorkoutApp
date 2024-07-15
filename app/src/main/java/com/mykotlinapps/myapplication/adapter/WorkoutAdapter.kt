package com.mykotlinapps.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.myapplication.databinding.WorkoutItemBinding
import com.mykotlinapps.myapplication.data.Workout

class WorkoutAdapter(private val workouts: List<Workout>) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = WorkoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.bind(workout)
    }

    override fun getItemCount(): Int = workouts.size

    inner class WorkoutViewHolder(private val binding: WorkoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            binding.workoutTitle.text = workout.title
            binding.workoutDescription.text = workout.description
        }
    }
}
