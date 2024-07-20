package com.mykotlinapps.bodybuilder.ui.add_character

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mykotlinapps.bodybuilder.Exercise
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.databinding.AddExcerciseLayoutBinding
import com.mykotlinapps.bodybuilder.ui.ItemsViewModel

class AddExcerciseFragment : Fragment() {
    private var _binding: AddExcerciseLayoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemsViewModel by activityViewModels()

    private val pickImageLauncher: ActivityResultLauncher<Array<String>> = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            binding.imageExercise.setImageURI(it)
            requireActivity().contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddExcerciseLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve exercise from arguments
        val exercise = arguments?.getParcelable<Exercise>("exercise")
        exercise?.let {
            // Set exercise details
            binding.textExerciseTitle.text = it.name
            binding.imageExercise.setImageURI(Uri.parse(it.gifUrl))
        }

        binding.buttonAddSet.setOnClickListener {
            addSetView()
        }

        binding.finishBtn.setOnClickListener {
            // Collect data from all sets
            val sets = mutableListOf<Pair<String, String>>()
            for (i in 0 until binding.containerReps.childCount) {
                val setView = binding.containerReps.getChildAt(i) as LinearLayout
                val kg = setView.findViewById<EditText>(R.id.kg_input).text.toString()
                val reps = setView.findViewById<EditText>(R.id.reps_input).text.toString()
                sets.add(Pair(kg, reps))
            }

            // Add exercise data to the ViewModel or next fragment
            exercise?.let {
                val newExercise = Exercise(
                    bodyPart = it.bodyPart,
                    equipment = it.equipment,
                    gifUrl = it.gifUrl,
                    id = it.id,
                    name = it.name,
                    target = it.target
                )

                viewModel.addExercise(newExercise)  // Ensure your ViewModel has this method
            }
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }

        binding.imageBtn.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }
    }

    private fun addSetView() {
        val setView = LayoutInflater.from(context).inflate(R.layout.item_set, binding.containerReps, false) as LinearLayout
        setView.findViewById<Button>(R.id.button_remove_set).setOnClickListener {
            binding.containerReps.removeView(setView)
        }
        binding.containerReps.addView(setView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
