package com.mykotlinapps.bodybuilder.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.Exercise
import com.mykotlinapps.bodybuilder.databinding.ItemExerciseBinding
import com.mykotlinapps.bodybuilder.ui.fragments.capitalizeWords
import java.util.Locale

class ExerciseAdapter(
    private val exercises: List<Exercise>,
    private val clickListener: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position], clickListener)
    }

    override fun getItemCount() = exercises.size

    class ExerciseViewHolder(private val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise, clickListener: (Exercise) -> Unit) {
            binding.exerciseName.text = exercise.name.capitalizeWords()
            binding.exerciseTarget.text = exercise.target
            binding.exerciseEquipment.text = exercise.equipment


            val url = exercise.gifUrl
            Log.d("ExerciseAdapter", "Loading URL: $url")

            Glide.with(binding.exerciseGif.context)
                .load(url)
                .placeholder(R.drawable.placeholder) // Optional placeholder

                .into(binding.exerciseGif)

            itemView.setOnClickListener { clickListener(exercise) }
        }
    }
}
// Extension function to capitalize words
fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.replaceFirstChar { char ->
    if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
} }