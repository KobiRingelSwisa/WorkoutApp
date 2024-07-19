package com.mykotlinapps.bodybuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mykotlinapps.bodybuilder.databinding.DialogPlansForDayBinding

class WorkoutPlansBottomSheetDialogFragment(private val date: String, private val workoutPlans: List<String>) : BottomSheetDialogFragment() {

    private var _binding: DialogPlansForDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogPlansForDayBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.title.text = "Plans for $date"

        // Set up RecyclerView with your adapter
        val adapter = WorkoutPlansAdapter(workoutPlans)
        binding.plansRecyclerView.adapter = adapter

        binding.btnHide.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
