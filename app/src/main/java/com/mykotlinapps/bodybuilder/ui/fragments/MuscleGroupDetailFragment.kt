package com.mykotlinapps.bodybuilder.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mykotlinapps.bodybuilder.data.adapter.WorkoutsAdapter
import com.mykotlinapps.bodybuilder.databinding.FragmentMuscleGroupDetailBinding
import com.mykotlinapps.bodybuilder.data.WorkoutTemplate

class MuscleGroupDetailFragment : Fragment() {

    private var _binding: FragmentMuscleGroupDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMuscleGroupDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val muscleGroup = arguments?.getString("muscleGroup") ?: return
        val performance = arguments?.getString("performance") ?: return
        val insights = arguments?.getString("insights") ?: return

        binding.muscleGroupTitle.text = muscleGroup
        binding.performanceTextView.text = performance
        binding.insightsTextView.text = insights

        // Setup RecyclerView for workouts (dummy data for now)
        val workouts = listOf(
            WorkoutTemplate("Workout 1", "Description 1"),
            WorkoutTemplate("Workout 2", "Description 2"),
            WorkoutTemplate("Workout 3", "Description 3")
        )

        binding.workoutsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.workoutsRecyclerView.adapter = WorkoutsAdapter(workouts) { template ->
            // Handle item click if necessary
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
