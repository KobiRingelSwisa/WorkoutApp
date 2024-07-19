package com.mykotlinapps.bodybuilder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.mykotlinapps.bodybuilder.databinding.DialogBodyStatsBinding

class BodyStatsDialogFragment : DialogFragment() {

    private var _binding: DialogBodyStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBodyStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val weight = binding.weightEditText.text.toString()
            val bodyFat = binding.bodyFatEditText.text.toString()
            val waistSize = binding.waistSizeEditText.text.toString()

            if (weight.isNotEmpty() && bodyFat.isNotEmpty() && waistSize.isNotEmpty()) {
                saveBodyStats(weight, bodyFat, waistSize)
                Toast.makeText(context, "Saved: Weight: $weight, Body Fat: $bodyFat, Waist Size: $waistSize", Toast.LENGTH_SHORT).show()
                dismiss()
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBodyStats(weight: String, bodyFat: String, waistSize: String) {
        val sharedPreferences = requireContext().getSharedPreferences("BodyStats", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("weight", weight)
            putString("bodyFat", bodyFat)
            putString("waistSize", waistSize)
            apply()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            (resources.displayMetrics.heightPixels * 0.65).toInt()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
