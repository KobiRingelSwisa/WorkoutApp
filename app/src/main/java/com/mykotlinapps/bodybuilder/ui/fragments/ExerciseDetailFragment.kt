package com.mykotlinapps.bodybuilder.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.Exercise
import com.mykotlinapps.bodybuilder.databinding.FragmentExerciseDetailBinding
import java.util.Locale

class ExerciseDetailFragment : Fragment() {

    private var _binding: FragmentExerciseDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the argument from the Bundle
        val exercise = arguments?.getParcelable<Exercise>("exercise")
        exercise?.let {
            it.name = it.name.capitalizeWords()
            binding.exercise = it
            binding.executePendingBindings()

            // Load image using Glide
            Glide.with(this)
                .load(it.gifUrl)
                .placeholder(R.drawable.placeholder)
                .into(binding.exerciseImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Extension function to capitalize words
fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.replaceFirstChar { char ->
    if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
} }
