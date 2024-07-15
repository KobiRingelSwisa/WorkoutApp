package com.mykotlinapps.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mykotlinapps.myapplication.adapter.GoalAdapter
import com.mykotlinapps.myapplication.data.WorkoutDatabase
import com.mykotlinapps.myapplication.databinding.FragmentHomeBinding
import com.mykotlinapps.myapplication.repository.GoalRepository
import com.mykotlinapps.myapplication.viewmodel.GoalViewModel
import com.mykotlinapps.myapplication.viewmodel.GoalViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val goalDao by lazy {
        WorkoutDatabase.getDatabase(requireContext()).goalDao()
    }

    private val goalRepository by lazy {
        GoalRepository(goalDao)
    }

    private val goalViewModel: GoalViewModel by viewModels {
        GoalViewModelFactory(goalRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GoalAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        goalViewModel.allGoals.observe(viewLifecycleOwner) { goals ->
            goals?.let { adapter.submitList(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
