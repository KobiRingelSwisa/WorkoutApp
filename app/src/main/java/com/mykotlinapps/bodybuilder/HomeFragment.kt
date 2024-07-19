package com.mykotlinapps.bodybuilder

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
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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

        setupCircularProgressBar()
        setupCalendar()
        setupRecentSessions()
        setupAddWorkoutFab()
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
            // Handle date change
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            showPlansForDayDialog(selectedDate.time)
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

        layoutParams.width = resources.displayMetrics.widthPixels / 2 // Set width to half the screen width
        parentLayout.layoutParams = layoutParams

        bottomSheetBinding.bodyStatsOption.setOnClickListener{
            Toast.makeText(context, "bodystats",Toast.LENGTH_SHORT).show()
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

    private fun showPlansForDayDialog(date: Date) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_plans_for_day, null)
        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val plansRecyclerView = dialogView.findViewById<RecyclerView>(R.id.plansRecyclerView)

        dialogTitle.text = "Plans for ${date.toLocaleString()}"

        plansRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        plansRecyclerView.adapter = PlansAdapter(getPlansForDate(date))

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun getPlansForDate(date: Date): List<Plan> {
        // Fetch plans for the given date from a data source
        // This is a placeholder implementation
        return listOf(
            Plan("Workout A", "Description of Workout A"),
            Plan("Workout B", "Description of Workout B")
        )
    }
}
