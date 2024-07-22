package com.mykotlinapps.bodybuilder.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.User
//import com.mykotlinapps.bodybuilder.data.User
import com.mykotlinapps.bodybuilder.data.WorkoutTemplate
import com.mykotlinapps.bodybuilder.databinding.FragmentPlansBinding
import com.mykotlinapps.bodybuilder.data.adapter.WorkoutsAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import java.util.UUID


class PlansFragment : Fragment() {

    private var _binding: FragmentPlansBinding? = null
    private val binding get() = _binding!!
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var fragmentContent: ScrollView
    private lateinit var adapter: WorkoutsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingAnimation = binding.loadingAnimation
        fragmentContent = binding.fragmentContent

        val button = view.findViewById<Button>(R.id.exe_btn)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_plansFragment_to_ExercisesDBFragment)
        }

        // Show loading animation and hide content initially
        showLoadingAnimation()

        // Simulate loading duration
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoadingAnimation()
        }, 3000) // 3 seconds delay

        setupRecyclerView()
        binding.generateWorkout.setOnClickListener {
            findNavController().navigate(R.id.action_plansFragment_to_generateWorkoutFragment)
        }
    }

    private fun setupRecyclerView() {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Fetch workouts from Firestore
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                val workouts = user?.workouts ?: mutableListOf()

                adapter = WorkoutsAdapter(workouts.toMutableList()) { workout ->
                    Log.d("Workout", workout.name.toString())
                    showTemplateDetails(workout)
                }
                binding.exerciseRecyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = this@PlansFragment.adapter
                }

                showEmptyState(binding.exerciseRecyclerView, binding.exerciseRecyclerViewEmptyText, workouts.isEmpty())

                // Attach ItemTouchHelper to RecyclerView
                val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
                itemTouchHelper.attachToRecyclerView(binding.exerciseRecyclerView)
            }
            .addOnFailureListener { exception ->
                // Handle any errors here
                Log.e("FirestoreError", "Error fetching workouts", exception)
                showEmptyState(binding.exerciseRecyclerView, binding.exerciseRecyclerViewEmptyText, true)
            }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            Log.d("position",position.toString())
            if (position != RecyclerView.NO_POSITION) {
                val workout = adapter.workoutTemplates[position]
                val workoutId = UUID.fromString(workout.id) // Convert back to UUID if needed
                adapter.removeItem(position)
                // Optionally, you can remove the item from Firestore here as well
                val db = FirebaseFirestore.getInstance()
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
                db.collection("users").document(userId).update("workouts", adapter.workoutTemplates)
            }
        }
    }

    private fun showEmptyState(recyclerView: RecyclerView, emptyTextView: TextView, isEmpty: Boolean) {
        if (isEmpty) {
            recyclerView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        }
    }

    private fun showTemplateDetails(template: WorkoutTemplate) {
        // Show detailed information about the selected template
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
