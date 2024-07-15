package com.mykotlinapps.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mykotlinapps.myapplication.data.Workout
import com.mykotlinapps.myapplication.databinding.FragmentAddEditWorkoutBinding

class AddEditFragment : Fragment() {

    private var _binding: FragmentAddEditWorkoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveWorkout()
        }
    }

    private fun saveWorkout() {
        val name = binding.nameEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val duration = binding.durationEditText.text.toString().toInt()
        val title = binding.titleEditText.text.toString()

        val workout = Workout(name = name, description = description, duration = duration, title = title)
        // Save workout to the database
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

