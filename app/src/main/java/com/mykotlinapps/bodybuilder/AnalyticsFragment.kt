package com.mykotlinapps.bodybuilder

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import android.widget.ScrollView

class AnalyticsFragment : Fragment() {

    private lateinit var muscleGroupRecyclerView: RecyclerView
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var fragmentContent: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analytics, container, false)

        loadingAnimation = view.findViewById(R.id.loading_animation)
        fragmentContent = view.findViewById(R.id.fragment_content)

        // Show loading animation and hide content initially
        showLoadingAnimation()

        // Simulate loading duration
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoadingAnimation()
        }, 3000) // 3 seconds delay

        return view    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        muscleGroupRecyclerView = view.findViewById(R.id.muscleGroupRecyclerView)
        setupMuscleGroupRecyclerView()
    }

    private fun setupMuscleGroupRecyclerView() {
        muscleGroupRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val muscleGroupList = listOf(
            MuscleGroupPerformance("Chest", "85%", "Strength training twice a week"),
            MuscleGroupPerformance("Back", "78%", "Increase reps for better performance"),
            MuscleGroupPerformance("Legs", "92%", "Great endurance, maintain current routine"),
            // Add more muscle groups as needed
        )

        muscleGroupRecyclerView.adapter =
            MuscleGroupAdapter(requireContext(), muscleGroupList) { muscleGroup ->
                openAnalyticsWindow(muscleGroup)
            }
    }

    private fun openAnalyticsWindow(muscleGroup: MuscleGroupPerformance) {
        // Handle opening analytics window for the chosen muscle group
        // For example, navigate to another fragment and pass the muscle group data
    }

    private fun showLoadingAnimation() {
        loadingAnimation.visibility = View.VISIBLE
        fragmentContent.visibility = View.GONE
    }

    private fun hideLoadingAnimation() {
        loadingAnimation.visibility = View.GONE
        fragmentContent.visibility = View.VISIBLE
    }
}
