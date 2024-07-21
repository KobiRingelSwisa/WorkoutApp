package com.mykotlinapps.bodybuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.data.Workout

class WorkoutTemplateAdapter(
    private val workouts: List<Workout>,
    private val onItemClick: (Workout) -> Unit
) : RecyclerView.Adapter<WorkoutTemplateAdapter.WorkoutTemplateViewHolder>() {

    class WorkoutTemplateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.templateName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.templateDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutTemplateViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout_template, parent, false)
        return WorkoutTemplateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutTemplateViewHolder, position: Int) {
        val workout = workouts[position]
        holder.nameTextView.text = workout.name
        holder.descriptionTextView.text = workout.duration
//        holder.itemView.setOnClickListener { onItemClick(workout) }
    }

    override fun getItemCount() = workouts.size
}
