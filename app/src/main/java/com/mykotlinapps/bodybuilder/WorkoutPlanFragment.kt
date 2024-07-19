package com.mykotlinapps.bodybuilder


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.data.Plan
import com.mykotlinapps.bodybuilder.data.PlansAdapter

class WorkoutPlanFragment : Fragment() {

    private lateinit var exerciseRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_single_workout_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseRecyclerView = view.findViewById(R.id.exerciseRecyclerView)
        setupExerciseRecyclerView()
    }

    private fun setupExerciseRecyclerView() {
        exerciseRecyclerView.layoutManager = LinearLayoutManager(context)

        val exerciseList = listOf(
            Plan("Push-ups", "Do 3 sets of 15 reps"),
            Plan("Squats", "Do 3 sets of 20 reps"),
            Plan("Lunges", "Do 3 sets of 15 reps each leg"),
            Plan("Plank", "Hold for 1 minute")
            // Add more exercises as needed
        )

        exerciseRecyclerView.adapter = PlansAdapter(exerciseList)
    }
}
