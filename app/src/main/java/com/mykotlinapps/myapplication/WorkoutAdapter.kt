package com.mykotlinapps.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.myapplication.databinding.WorkoutItemBinding

class WorkoutAdapter : ListAdapter<Workout, WorkoutAdapter.WorkoutViewHolder>(WorkoutsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = WorkoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class WorkoutViewHolder(private val binding: WorkoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            binding.workoutName.text = workout.name
            binding.workoutDuration.text = workout.duration.toString()
        }
    }

    class WorkoutsComparator : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
