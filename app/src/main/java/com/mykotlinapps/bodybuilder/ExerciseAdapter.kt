//package com.mykotlinapps.bodybuilder
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.mykotlinapps.bodybuilder.data.Exercise
//
//class ExerciseAdapter(private val exercises: List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
//
//    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val nameTextView: TextView = itemView.findViewById(R.id.textExerciseTitle)
//        val targetTextView: TextView = itemView.findViewById(R.id.textExerciseDetails)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_exercise, parent, false)
//        return ExerciseViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
//        val exercise = exercises[position]
//        holder.nameTextView.text = exercise.name
//        holder.targetTextView.text = exercise.target
//    }
//
//    override fun getItemCount() = exercises.size
//}
