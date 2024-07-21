package com.mykotlinapps.bodybuilder.ui.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.mykotlinapps.bodybuilder.databinding.BottomSheetAddWorkoutBinding
import com.mykotlinapps.bodybuilder.databinding.FragmentHomeBinding
import java.util.*
import android.Manifest
import android.app.AlertDialog
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import com.airbnb.lottie.LottieAnimationView
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.adapter.RecentSessionsAdapter
import com.mykotlinapps.bodybuilder.data.WorkoutSession
import com.mykotlinapps.bodybuilder.data.Plan
import com.mykotlinapps.bodybuilder.data.PlansAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var circularProgressBar: CircularProgressBar
    private lateinit var progressPercentage: TextView
    private lateinit var calendarView: CalendarView
    private lateinit var recentSessionsRecyclerView: RecyclerView
    private lateinit var addWorkoutFab: FloatingActionButton
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var fragmentContent: View
    private lateinit var plansForDayLayout: LinearLayout
    private lateinit var plansRecyclerView: RecyclerView
    private lateinit var btnHide: Button
    private lateinit var title: TextView

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

    // Initialize the requestCameraPermissionLauncher in the Fragment
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Open the camera.
            openCamera()
        } else {
            // Permission is denied. Show a message.
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

        circularProgressBar = view.findViewById(R.id.fitnessLevelProgress)
        progressPercentage = view.findViewById(R.id.progressPercentage)
        calendarView = view.findViewById(R.id.calendarView)
        recentSessionsRecyclerView = view.findViewById(R.id.recentSessionsRecyclerView)
        addWorkoutFab = view.findViewById(R.id.addWorkoutFab)
        loadingAnimation = view.findViewById(R.id.loading_animation)
        fragmentContent = view.findViewById(R.id.fragment_content)
        plansForDayLayout = binding.plansForDayLayout
        plansRecyclerView = binding.plansRecyclerView
        btnHide = binding.btnHide
        title = binding.title

        // Show loading animation and hide content initially
        showLoadingAnimation()

        // Simulate loading duration
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoadingAnimation()
        }, 3000) // 3 seconds delay

        setupCircularProgressBar()
        setupCalendar()
        setupRecentSessions()
        setupAddWorkoutFab()
        setupPlansForDay()
    }

    private fun setupCircularProgressBar() {
        val fitnessLevel = 75 // Example value

        circularProgressBar.setProgressWithAnimation(fitnessLevel.toFloat(), 1000)
        progressPercentage.text = "$fitnessLevel%"

        circularProgressBar.setOnClickListener {
            // Navigate to the analytics page
            navigateToAnalytics()
        }
    }

    private fun setupCalendar() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$year-${month + 1}-$dayOfMonth"
            val plans = workoutPlans[date] ?: emptyList()
            showPlansForDay(date, plans)
        }
    }

    private fun setupRecentSessions() {
        recentSessionsRecyclerView.layoutManager = LinearLayoutManager(context)
        recentSessionsRecyclerView.adapter = RecentSessionsAdapter(getRecentSessions())
    }

    private fun getRecentSessions(): List<WorkoutSession> {
        // Fetch recent sessions from a data source
        return listOf(
            WorkoutSession("Morning Run", "30 min", Date()),
            WorkoutSession("Evening Yoga", "45 min", Date())
        )
    }

    private fun setupAddWorkoutFab() {
        addWorkoutFab.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialog)
        val bottomSheetBinding = BottomSheetAddWorkoutBinding.inflate(layoutInflater)

        // Set up the bottom sheet options here

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
            Toast.makeText(context, "freestyle",Toast.LENGTH_SHORT).show()
        }
        bottomSheetBinding.progressPhotoOption.setOnClickListener {
            checkCameraPermission()
        }

        bottomSheetDialog.show()
    }

    private fun navigateToAnalytics() {
        // Navigation logic to the analytics page
        findNavController().navigate(R.id.action_homeFragment_to_analyticsFragment)
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                openCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected.
                Toast.makeText(context, "Camera permission is needed to take a progress photo", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Directly ask for the permission
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    private fun showPlansForDay(date: String, plans: List<Plan>) {
        title.text = "Plans for $date"
        plansRecyclerView.adapter = PlansAdapter(plans)
        plansForDayLayout.visibility = View.VISIBLE
    }

    private fun setupPlansForDay() {
        btnHide.setOnClickListener {
            plansForDayLayout.visibility = View.GONE
        }
    }

    private fun getPlansForDate(date: Date): List<Plan> {
        // Fetch plans for the given date from a data source
        // This is a placeholder implementation
        return listOf(
            Plan("Workout A", "Description of Workout A"),
            Plan("Workout B", "Description of Workout B")
        )
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