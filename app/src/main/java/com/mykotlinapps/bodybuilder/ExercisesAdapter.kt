package com.mykotlinapps.bodybuilder

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.databinding.AddExcerciseLayoutBinding

class ExercisesAdapter : RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>() {

    private val exercises = mutableListOf<Exercise>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = AddExcerciseLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
        notifyItemInserted(exercises.size - 1)
    }

    inner class ExerciseViewHolder(private val binding: AddExcerciseLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonAddSet.setOnClickListener {
                addSet()
            }
        }

        fun bind(exercise: Exercise) {
            binding.textExerciseTitle.text = exercise.name
            binding.imageExercise.setImageURI(Uri.parse(exercise.gifUrl))
            // Bind other fields as needed
        }

        private fun addSet() {
            val setView = LayoutInflater.from(itemView.context).inflate(R.layout.item_set, binding.containerReps, false)
            binding.containerReps.addView(setView)

            setView.findViewById<Button>(R.id.button_remove_set).setOnClickListener {
                binding.containerReps.removeView(setView)
            }
        }
    }
}
