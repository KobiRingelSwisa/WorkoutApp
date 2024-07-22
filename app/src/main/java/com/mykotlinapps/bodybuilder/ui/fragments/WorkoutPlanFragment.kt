package com.mykotlinapps.bodybuilder.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mykotlinapps.bodybuilder.data.Exercise
import com.mykotlinapps.bodybuilder.data.adapter.ExercisesAdapter
import com.mykotlinapps.bodybuilder.data.Plan
import com.mykotlinapps.bodybuilder.data.PlansAdapter
import com.mykotlinapps.bodybuilder.databinding.FragmentWorkoutPlanBinding

class WorkoutPlanFragment : Fragment() {

    private var _binding: FragmentWorkoutPlanBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchView: SearchView
    private val exercises = ArrayList<Exercise>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupExerciseRecyclerView()
    }

    private fun setupExerciseRecyclerView() {
        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(context)

        val exerciseList = listOf(
            Plan("Push-ups", "Do 3 sets of 15 reps"),
            Plan("Squats", "Do 3 sets of 20 reps"),
            Plan("Lunges", "Do 3 sets of 15 reps each leg"),
            Plan("Plank", "Hold for 1 minute")
            // Add more exercises as needed
        )

        binding.exerciseRecyclerView.adapter = PlansAdapter(exerciseList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTemplatesList(): List<Plan> {
        // Populate with your template plans
        return listOf(
            Plan("Template 1", "Description of Template 1"),
            Plan("Template 2", "Description of Template 2")
            // Add more templates as needed
        )
    }
}
