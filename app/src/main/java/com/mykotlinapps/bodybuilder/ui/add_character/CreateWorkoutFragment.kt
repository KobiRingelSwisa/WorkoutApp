package com.mykotlinapps.bodybuilder.ui.add_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mykotlinapps.bodybuilder.Exercise
import com.mykotlinapps.bodybuilder.ExercisesAdapter
import com.mykotlinapps.bodybuilder.databinding.FragmentCreateWorkoutBinding

class CreateWorkoutFragment : Fragment() {

    private var _binding: FragmentCreateWorkoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var exercisesAdapter: ExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exercisesAdapter = ExercisesAdapter()
        binding.recyclerViewExercises.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exercisesAdapter
        }

        binding.buttonAddExercise.setOnClickListener {
            // Add a new exercise to the list
            val defaultExercise = Exercise(
                bodyPart = "legs",
                equipment = "dumbbell",
                gifUrl = "http://example.com/image.gif",
                id = "12345",
                name = "Squats",
                target = "quads"
            )
            exercisesAdapter.addExercise(defaultExercise)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
