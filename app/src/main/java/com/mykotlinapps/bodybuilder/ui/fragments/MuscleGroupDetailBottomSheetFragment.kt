package com.mykotlinapps.bodybuilder.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mykotlinapps.bodybuilder.data.adapter.WorkoutsAdapter
import com.mykotlinapps.bodybuilder.databinding.FragmentMuscleGroupDetailBinding
import com.mykotlinapps.bodybuilder.data.WorkoutTemplate

class MuscleGroupDetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMuscleGroupDetailBinding? = null
    private val binding get() = _binding!!

    // Define the workouts list
    private val workouts: List<WorkoutTemplate> = listOf(
        WorkoutTemplate("Push-ups", "3 sets of 15 reps"),
        WorkoutTemplate("Bench Press", "4 sets of 10 reps"),
        WorkoutTemplate("Chest Fly", "3 sets of 12 reps")
        // Add more workout templates as needed
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMuscleGroupDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val muscleGroupName = arguments?.getString("muscleGroupName") ?: return
        val performance = arguments?.getString("performance") ?: return
        val insights = arguments?.getString("insights") ?: return

        binding.muscleGroupTitle.text = muscleGroupName
        binding.performanceTextView.text = performance
        binding.insightsTextView.text = insights

        // Set up the RecyclerView with the workouts adapter
        binding.workoutsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.workoutsRecyclerView.adapter = WorkoutsAdapter(workouts) { template ->
            // Handle item click if necessary
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "MuscleGroupDetailBottomSheet"

        fun newInstance(muscleGroupName: String, performance: String, insights: String): MuscleGroupDetailBottomSheetFragment {
            val fragment = MuscleGroupDetailBottomSheetFragment()
            val args = Bundle()
            args.putString("muscleGroupName", muscleGroupName)
            args.putString("performance", performance)
            args.putString("insights", insights)
            fragment.arguments = args
            return fragment
        }
    }
}
