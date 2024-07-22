package com.mykotlinapps.bodybuilder.data.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.Exercise
import com.mykotlinapps.bodybuilder.data.Set
import com.mykotlinapps.bodybuilder.databinding.AddExcerciseLayoutBinding

class ExercisesAdapter(
    private val exercises: MutableList<Exercise>,
    private val onExerciseSelected: (Exercise) -> Unit,
    private val onExerciseDeselected: (Exercise) -> Unit
) : RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>() {

    private val selectedExercises = mutableSetOf<Exercise>()
    private val sets: MutableList<Set> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = AddExcerciseLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise, sets)
    }

    override fun getItemCount(): Int = exercises.size

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
        notifyItemInserted(exercises.size - 1)
    }

    fun removeExercise(position: Int) {
        exercises.removeAt(position)
        notifyItemRemoved(position)
    }

    fun moveExercise(fromPosition: Int, toPosition: Int) {
        val movedExercise = exercises.removeAt(fromPosition)
        exercises.add(toPosition, movedExercise)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun getSelectedExercises(): List<Exercise> = selectedExercises.toList()

    inner class ExerciseViewHolder(private val binding: AddExcerciseLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonAddSet.setOnClickListener {
                addSet()
            }

            binding.expandCollapseButton.setOnClickListener {
                toggleSetsVisibility()
            }

            binding.exerciseCheckBox.setOnCheckedChangeListener { _, isChecked ->
                val exercise = exercises[adapterPosition]
                if (isChecked) {
                    selectedExercises.add(exercise)
                    onExerciseSelected(exercise)
                } else {
                    selectedExercises.remove(exercise)
                    onExerciseDeselected(exercise)
                }
            }
        }

        fun bind(exercise: Exercise, sets: MutableList<Set>) {
            binding.textExerciseTitle.text = exercise.name
            binding.imageExercise.setImageURI(Uri.parse(exercise.gifUrl))
            binding.containerReps.removeAllViews()
            binding.exerciseCheckBox.isChecked = selectedExercises.contains(exercise)

            for (set in sets) {
                val setView = LayoutInflater.from(binding.containerReps.context)
                    .inflate(R.layout.item_set, binding.containerReps, false)
                val kgInput = setView.findViewById<EditText>(R.id.kg_input)
                val repsInput = setView.findViewById<EditText>(R.id.reps_input)
                kgInput.setText(set.kg.toString())
                repsInput.setText(set.reps.toString())
                setView.findViewById<Button>(R.id.button_remove_set).setOnClickListener {
                    sets.remove(set)
                    notifyItemChanged(adapterPosition)
                }
                binding.containerReps.addView(setView)
            }
        }

        private fun addSet() {
            val setView = LayoutInflater.from(itemView.context).inflate(R.layout.item_set, binding.containerReps, false)
            binding.containerReps.addView(setView)

            setView.findViewById<Button>(R.id.button_remove_set).setOnClickListener {
                binding.containerReps.removeView(setView)
            }
        }

        private fun toggleSetsVisibility() {
            if (binding.containerReps.visibility == View.GONE) {
                binding.containerReps.visibility = View.VISIBLE
                binding.expandCollapseButton.text = "Collapse"
            } else {
                binding.containerReps.visibility = View.GONE
                binding.expandCollapseButton.text = "Expand"
            }
        }
    }
}
