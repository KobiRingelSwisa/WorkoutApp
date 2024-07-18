package com.mykotlinapps.bodybuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AnalyticsFragment : Fragment() {

    private lateinit var muscleGroupRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        muscleGroupRecyclerView = view.findViewById(R.id.muscleGroupRecyclerView)
        setupMuscleGroupRecyclerView()
    }

    private fun setupMuscleGroupRecyclerView() {
        muscleGroupRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val muscleGroupList = listOf(
            MuscleGroupPerformance("Chest", "85%", "Strength training twice a week"),
            MuscleGroupPerformance("Back", "78%", "Increase reps for better performance"),
            MuscleGroupPerformance("Legs", "92%", "Great endurance, maintain current routine"),
            // Add more muscle groups as needed
        )

        muscleGroupRecyclerView.adapter = MuscleGroupAdapter(requireContext(), muscleGroupList)
    }
}
