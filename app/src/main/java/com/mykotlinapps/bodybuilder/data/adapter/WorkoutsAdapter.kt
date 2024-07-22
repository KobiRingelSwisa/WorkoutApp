package com.mykotlinapps.bodybuilder.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.Workout
import com.mykotlinapps.bodybuilder.data.WorkoutTemplate

class WorkoutsAdapter(
    val workoutTemplates: MutableList<Workout>,
    private val onItemClick: (WorkoutTemplate) -> Unit
) : RecyclerView.Adapter<WorkoutsAdapter.WorkoutTemplateViewHolder>() {

    class WorkoutTemplateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.templateName)
        val dateTextView: TextView = itemView.findViewById(R.id.templateDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutTemplateViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout_template, parent, false)
        return WorkoutTemplateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutTemplateViewHolder, position: Int) {
        val template = workoutTemplates[position]
        holder.nameTextView.text = template.name
        holder.dateTextView.text = template.date.toString()
//        holder.itemView.setOnClickListener { onItemClick(template) }
    }

    override fun getItemCount() = workoutTemplates.size

    fun removeItem(position: Int) {
        workoutTemplates.removeAt(position)
        notifyItemRemoved(position)
    }
}
