package com.mykotlinapps.bodybuilder.ui.single_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.mykotlinapps.bodybuilder.databinding.DetailItemLayoutBinding
import com.mykotlinapps.bodybuilder.data.viewmodel.ItemsViewModel

class DetailItemFragment: Fragment() {

    var _binding: DetailItemLayoutBinding? = null
    val binding get() = _binding!!
    val viewModel : ItemsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailItemLayoutBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.chosenItem.observe(viewLifecycleOwner){
            binding.itemTitle.text = it.name
            binding.itemDesc.text = it.bodyPart
            Glide.with(requireContext()).load(it.gifUrl).circleCrop().into(binding.itemImg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}