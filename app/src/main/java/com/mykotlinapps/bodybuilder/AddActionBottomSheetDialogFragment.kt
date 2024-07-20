package com.mykotlinapps.bodybuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mykotlinapps.bodybuilder.databinding.DialogPlansForDayBinding

class AddActionBottomSheetDialogFragment() : BottomSheetDialogFragment() {

    private var _binding: DialogPlansForDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var date: String
    private lateinit var workoutPlans: List<String>

    companion object {
        private const val ARG_DATE = "date"
        private const val ARG_WORKOUT_PLANS = "workout_plans"

        fun newInstance(date: String, workoutPlans: List<String>): AddActionBottomSheetDialogFragment {
            val fragment = AddActionBottomSheetDialogFragment()
            val args = Bundle()
            args.putString(ARG_DATE, date)
            args.putStringArrayList(ARG_WORKOUT_PLANS, ArrayList(workoutPlans))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString(ARG_DATE) ?: ""
            workoutPlans = it.getStringArrayList(ARG_WORKOUT_PLANS) ?: emptyList()
        }
    }

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
