package com.mykotlinapps.bodybuilder.ui.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mykotlinapps.bodybuilder.databinding.BottomSheetAddWorkoutBinding
import com.mykotlinapps.bodybuilder.databinding.FragmentHomeBinding
import java.util.*
import android.Manifest
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.airbnb.lottie.LottieAnimationView
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.adapter.RecentSessionsAdapter
import com.mykotlinapps.bodybuilder.data.WorkoutSession
import com.mykotlinapps.bodybuilder.data.Plan
import com.mykotlinapps.bodybuilder.data.PlansAdapter
import com.mykotlinapps.bodybuilder.ui.StrengthScoreView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val workoutPlans = mapOf(
        "2023-07-19" to listOf(
            Plan("Workout A", "Description of Workout A"),
            Plan("Workout B", "Description of Workout B")
        ),
        "2023-07-20" to listOf(
            Plan("Workout C", "Description of Workout C"),
            Plan("Workout D", "Description of Workout D")
        )
    )

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            showLoadingAnimation()
            viewLifecycleOwner.lifecycleScope.launch {
                delay(3000)
                hideLoadingAnimation()
            }

            setupStrengthScoreView()
            setupCalendar()
            setupRecentSessions()
            setupAddWorkoutFab()
            setupPlansForDay()
        }
    }

    private fun setupStrengthScoreView() {
        val strengthScore = 278 // Example value
        binding.strengthScoreView.setScore(strengthScore)
        binding.strengthScoreView.setOnClickListener {
            navigateToAnalytics()
        }
    }

    private fun setupCalendar() {
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$year-${month + 1}-$dayOfMonth"
            val plans = workoutPlans[date] ?: emptyList()
            showPlansForDay(date, plans)
        }
    }

    private fun setupRecentSessions() {
        binding.recentSessionsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.recentSessionsRecyclerView.adapter = RecentSessionsAdapter(getRecentSessions())
    }

    private fun getRecentSessions(): List<WorkoutSession> {
        return listOf(
            WorkoutSession("Morning Run", "30 min", Date()),
            WorkoutSession("Evening Yoga", "45 min", Date())
        )
    }

    private fun setupAddWorkoutFab() {
        binding.addWorkoutFab.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialog)
        val bottomSheetBinding = BottomSheetAddWorkoutBinding.inflate(layoutInflater)

        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        val parentLayout = bottomSheetBinding.root.parent as View
        val layoutParams = parentLayout.layoutParams

        layoutParams.width = resources.displayMetrics.widthPixels
        parentLayout.layoutParams = layoutParams

        bottomSheetBinding.bodyStatsOption.setOnClickListener{
            BodyStatsDialogFragment().show(childFragmentManager, "BodyStatsDialogFragment")
            bottomSheetDialog.dismiss()
        }
        bottomSheetBinding.plannedWorkoutOption.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_workoutPlanFragment)
            bottomSheetDialog.dismiss()
        }
        bottomSheetBinding.freestyleWorkoutOption.setOnClickListener {
            Toast.makeText(context, "freestyle", Toast.LENGTH_SHORT).show()
        }
        bottomSheetBinding.progressPhotoOption.setOnClickListener {
            checkCameraPermission()
        }

        bottomSheetDialog.show()
    }

    private fun navigateToAnalytics() {
        findNavController().navigate(R.id.action_homeFragment_to_analyticsFragment)
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(context, "Camera permission is needed to take a progress photo", Toast.LENGTH_SHORT).show()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    private fun showPlansForDay(date: String, plans: List<Plan>) {
        binding.title.text = "Plans for $date"
        binding.plansRecyclerView.adapter = PlansAdapter(plans)
        binding.plansForDayLayout.visibility = View.VISIBLE
    }

    private fun setupPlansForDay() {
        binding.btnHide.setOnClickListener {
            binding.plansForDayLayout.visibility = View.GONE
        }
    }

    private fun showLoadingAnimation() {
        binding.loadingAnimation.visibility = View.VISIBLE
        binding.fragmentContent.visibility = View.GONE
    }

    private fun hideLoadingAnimation() {
        binding.loadingAnimation.visibility = View.GONE
        binding.fragmentContent.visibility = View.VISIBLE
    }
}
