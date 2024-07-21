package com.mykotlinapps.bodybuilder

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mykotlinapps.bodybuilder.data.Exercise
import com.mykotlinapps.bodybuilder.data.Workout
import com.mykotlinapps.bodybuilder.databinding.DialogAddExerciseBinding
import com.mykotlinapps.bodybuilder.databinding.FragmentGenerateWorkoutBinding
import java.util.Date

class GenerateWorkoutFragment : Fragment() {

    private var _binding: FragmentGenerateWorkoutBinding? = null
    private val binding get() = _binding!!
    private val exercises = mutableListOf<Exercise>()
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private val bodyParts = listOf(
        "back", "cardio", "chest", "lower arms", "lower legs", "neck",
        "shoulders", "upper arms", "upper legs", "waist"
    )

    private val targets = listOf(
        "abductors", "abs", "adductors", "biceps", "calves", "cardiovascular system",
        "delts", "forearms", "glutes", "hamstrings", "lats", "levator scapulae",
        "pectorals", "quads", "serratus anterior", "spine", "traps", "triceps",
        "upper back"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenerateWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.btnAddExercise.setOnClickListener {
            showAddExerciseDialog()
        }

        binding.btnSaveWorkout.setOnClickListener {
            saveWorkout()
        }
    }

    private fun showAddExerciseDialog() {
        val dialogBinding = DialogAddExerciseBinding.inflate(LayoutInflater.from(context))
        val dialogBuilder = AlertDialog.Builder(context)
            .setTitle("Add Exercise")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                val bodyPart = dialogBinding.etBodyPart.text.toString()
                val equipment = dialogBinding.etEquipment.text.toString()
                val gifUrl = "" // dialogBinding.etGifUrl.text.toString()
                val id = db.collection("exercises").document().id
                val name = dialogBinding.etName.text.toString()
                val target = dialogBinding.etTarget.text.toString()
                val exercise = Exercise(bodyPart, equipment, gifUrl, id, name, target)
                addExerciseView(exercise)
                exercises.add(exercise)
            }
            .setNegativeButton("Cancel", null)
            .create()

        setupBodyPartInput(dialogBinding.etBodyPart, dialogBinding.etTarget)

        dialogBuilder.show()
    }

    private fun setupBodyPartInput(etBodyPart: EditText, etTarget: EditText) {
        etBodyPart.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showBodyPartOptions(etBodyPart)
            }
        }

        etTarget.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showTargetOptions(etTarget)
            }
        }
    }

    private fun showBodyPartOptions(etBodyPart: EditText) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Body Part")
        builder.setItems(bodyParts.toTypedArray()) { _, which ->
            etBodyPart.setText(bodyParts[which])
        }
        builder.show()
    }

    private fun showTargetOptions(etTarget: EditText) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Target")
        builder.setItems(targets.toTypedArray()) { _, which ->
            etTarget.setText(targets[which])
        }
        builder.show()
    }

    private fun addExerciseView(exercise: Exercise) {
        val exerciseView = LayoutInflater.from(context).inflate(R.layout.item_exercise, binding.exercisesContainer, false)
        val textExerciseTitle = exerciseView.findViewById<TextView>(R.id.textExerciseTitle)
        val textExerciseDetails = exerciseView.findViewById<TextView>(R.id.textExerciseDetails)

        textExerciseTitle.text = exercise.name
        textExerciseDetails.text = "Body Part: ${exercise.bodyPart}\nTarget: ${exercise.target}\nEquipment: ${exercise.equipment}"

        binding.exercisesContainer.addView(exerciseView)
    }

    private fun saveWorkout() {
        val workoutName = binding.etWorkoutName.text.toString()
        if (workoutName.isBlank()) {
            Toast.makeText(context, "Workout name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = auth.currentUser?.uid ?: return
        val workout = Workout(workoutName, "Custom duration", Date(), exercises)

        db.collection("users").document(userId).update("workouts", FieldValue.arrayUnion(workout))
            .addOnSuccessListener {
                Toast.makeText(context, "Workout saved successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_generateWorkoutFragment_to_plansFragment)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to save workout: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
