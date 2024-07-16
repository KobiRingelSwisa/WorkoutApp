package com.mykotlinapps.myapplication.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mykotlinapps.myapplication.adapter.GoalAdapter
import com.mykotlinapps.myapplication.adapter.WorkoutAdapter
import com.mykotlinapps.myapplication.data.WorkoutDatabase
import com.mykotlinapps.myapplication.databinding.FragmentHomeBinding
import com.mykotlinapps.myapplication.repository.GoalRepository
import com.mykotlinapps.myapplication.ui.activities.SettingsActivity
import com.mykotlinapps.myapplication.viewmodel.GoalViewModel
import com.mykotlinapps.myapplication.viewmodel.GoalViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val goalDao by lazy {
        WorkoutDatabase.getDatabase(requireContext()).goalDao()
    }

    private val goalRepository by lazy {
        GoalRepository(goalDao)
    }

    private val goalViewModel: GoalViewModel by viewModels {
        GoalViewModelFactory(goalRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Welcome Message
        binding.welcomeMessage.text = "Welcome back!"

        // Workout Summary
        binding.workoutSummary.text = "No workout scheduled for today."

        // Setup Quick Access Buttons
        binding.startWorkoutButton.setOnClickListener {
            // Navigate to start workout screen
        }

        binding.workoutPlansButton.setOnClickListener {
            // Navigate to workout plans screen
        }

        binding.progressButton.setOnClickListener {
            // Navigate to progress screen
        }

        // Setup RecyclerViews
        val goalAdapter = GoalAdapter()
        binding.goalsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.goalsRecyclerView.adapter = goalAdapter

        goalViewModel.allGoals.observe(viewLifecycleOwner) { goals ->
            goals?.let { goalAdapter.submitList(it) }
        }

        // Setup Recent Workouts RecyclerView
        val workoutAdapter = WorkoutAdapter(emptyList()) // Pass an empty list initially
        binding.recentWorkoutsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.recentWorkoutsRecyclerView.adapter = workoutAdapter

        // Fetch recent workouts from your data source and update adapter
        // workoutViewModel.recentWorkouts.observe(viewLifecycleOwner) { workouts ->
        //     workouts?.let { workoutAdapter.submitList(it) }
        // }

        // Motivational Quote
        binding.motivationalQuote.text = "Believe you can and you're halfway there."

        binding.settingsButton.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
