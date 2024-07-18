package com.mykotlinapps.bodybuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.databinding.FragmentWorkoutBinding

class WorkoutFragment : Fragment() {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root

        // TODO: Implement search functionality
        // TODO: Setup RecyclerView for exercises
        // TODO: Implement swipe-to-delete and drag-to-reorder functionality


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}