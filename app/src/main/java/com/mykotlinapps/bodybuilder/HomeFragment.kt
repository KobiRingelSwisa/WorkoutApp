package com.mykotlinapps.bodybuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.mykotlinapps.bodybuilder.R
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var circularProgressBar: CircularProgressBar
    private lateinit var progressPercentage: TextView
    private lateinit var calendarView: CalendarView
    private lateinit var recentSessionsRecyclerView: RecyclerView
    private lateinit var addWorkoutFab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
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
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_add_workout, null)

        // Set up the bottom sheet options here

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun navigateToAnalytics() {
        // Navigation logic to the analytics page
        findNavController().navigate(R.id.action_homeFragment_to_analyticsFragment)
    }
}
