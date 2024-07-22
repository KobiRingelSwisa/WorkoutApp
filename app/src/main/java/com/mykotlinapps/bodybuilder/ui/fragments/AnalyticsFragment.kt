package com.mykotlinapps.bodybuilder.ui.fragments

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mykotlinapps.bodybuilder.data.adapter.MuscleGroupAdapter
import com.mykotlinapps.bodybuilder.data.MuscleGroupPerformance
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var muscleGroupRecyclerView: RecyclerView
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var fragmentContent: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingAnimation = binding.loadingAnimation
        fragmentContent = binding.fragmentContent

        // Show loading animation and hide content initially
        showLoadingAnimation()

        // Simulate loading duration
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoadingAnimation()
        }, 3000) // 3 seconds delay

        setupMuscleGroupRecyclerView()

        // Load the SVG file into the custom view
        binding.svgImageView.setSVG(R.raw.full_body_muscles)
    }

    private fun setupMuscleGroupRecyclerView() {
        muscleGroupRecyclerView = binding.muscleGroupRecyclerView
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Define span sizes
                return when (position) {
                    // First row: 3 buttons, each takes 1 span
                    in 0..2 -> 1
                    // Second row: 3 buttons, each takes 1 span
                    in 3..5 -> 1
                    // Third row: "Glutes" button spans 3 columns to center it
                    6 -> 3
                    else -> 1
                }
            }
        }
        muscleGroupRecyclerView.layoutManager = gridLayoutManager

        val muscleGroupList = listOf(
            MuscleGroupPerformance("Chest", "85%", "Strength training twice a week"),
            MuscleGroupPerformance("Back", "78%", "Increase reps for better performance"),
            MuscleGroupPerformance("Legs", "92%", "Great endurance, maintain current routine"),
            MuscleGroupPerformance("Arms", "80%", "Focus on bicep and tricep isolation exercises"),
            MuscleGroupPerformance("Shoulders", "75%", "Include more compound movements"),
            MuscleGroupPerformance("Abs", "88%", "Core strengthening exercises are working well"),
            MuscleGroupPerformance("Glutes", "90%", "Excellent progress, keep up the good work")
        )

        muscleGroupRecyclerView.adapter =
            MuscleGroupAdapter(muscleGroupList) { muscleGroup ->
                openAnalyticsWindow(muscleGroup)
            }
    }

    private fun getMuscleGroups(): List<MuscleGroupPerformance> {
        // Provide the data for muscle groups
        return listOf(
            MuscleGroupPerformance("Chest", "Performance for chest", "Insights for chest"),
            MuscleGroupPerformance("Back", "Performance for back", "Insights for back")
            // Add more muscle groups as needed
        )
    }

    private fun openAnalyticsWindow(muscleGroup: MuscleGroupPerformance) {
        val dialogFragment = MuscleGroupDetailBottomSheetFragment.newInstance(
            muscleGroup.muscleGroup,
            muscleGroup.performance,
            muscleGroup.insights
        )
        dialogFragment.show(requireActivity().supportFragmentManager, MuscleGroupDetailBottomSheetFragment.TAG)
    }

    private fun showLoadingAnimation() {
        loadingAnimation.visibility = View.VISIBLE
        fragmentContent.visibility = View.GONE
    }

    private fun hideLoadingAnimation() {
        loadingAnimation.visibility = View.GONE
        fragmentContent.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}