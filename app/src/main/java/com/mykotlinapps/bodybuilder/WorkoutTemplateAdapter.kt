package com.mykotlinapps.bodybuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.data.WorkoutTemplate

class WorkoutTemplateAdapter(
    private val workoutTemplates: List<WorkoutTemplate>,
    private val onTemplateClick: (WorkoutTemplate) -> Unit
) : RecyclerView.Adapter<WorkoutTemplateAdapter.WorkoutTemplateViewHolder>() {

    class WorkoutTemplateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.templateNameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.templateDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutTemplateViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout_template, parent, false)
        return WorkoutTemplateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutTemplateViewHolder, position: Int) {
        val template = workoutTemplates[position]
        holder.nameTextView.text = template.name
        holder.descriptionTextView.text = template.description
        holder.itemView.setOnClickListener { onTemplateClick(template) }
    }

    override fun getItemCount() = workoutTemplates.size
}
