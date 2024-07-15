package com.mykotlinapps.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mykotlinapps.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val goalViewModel: GoalViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.viewModel = goalViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = GoalAdapter()
        binding.goalsRecyclerView.adapter = adapter

        goalViewModel.activeGoals.observe(viewLifecycleOwner, Observer { goals ->
            goals?.let {
                adapter.submitList(it)
                if (it.isEmpty()) {
                    binding.goalsText.text = "No active goals. Set your goals!"
                } else {
                    binding.goalsText.text = "Your Goals"
                }
            }
        })

        binding.addGoalButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_add)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
