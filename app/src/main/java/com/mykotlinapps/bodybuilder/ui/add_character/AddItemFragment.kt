package com.mykotlinapps.bodybuilder.ui.add_character

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mykotlinapps.bodybuilder.data.Item
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.databinding.AddItemLayoutBinding
import com.mykotlinapps.bodybuilder.ui.ItemsViewModel

class AddItemFragment : Fragment() {
    private  var _binding : AddItemLayoutBinding? = null
    private  val binding get() = _binding !!
    private var imageUri : Uri? = null
    private var gifUrl : Uri? = null
    private val target = "YourTarget"
    private  val viewModel : ItemsViewModel by activityViewModels()

    val pickImageLuncher: ActivityResultLauncher<Array<String>> = registerForActivityResult(
        ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            binding.resultImage.setImageURI(it)
            requireActivity().contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imageUri = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddItemLayoutBinding.inflate(inflater,container,false)
        binding.finishBtn.setOnClickListener{
            val bundle = bundleOf("title" to binding.itemTitle.text.toString(),"description" to binding.itemDescription.text.toString())
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)

              val item= Item(
                  binding.itemTitle.text.toString(),
                  binding.itemDescription.text.toString(),
                  imageUri.toString(),
                  gifUrl.toString(),
                  target,
                  "This is a detailed description of the exercise, including instructions on how to perform it, benefits, and any other relevant information."
              )
            //ItemManager.add(item)
            viewModel.addItem(item)
            findNavController().navigate((R.id.action_addItemFragment_to_allItemsFragment))
        }

        binding.imageBtn.setOnClickListener {
            pickImageLuncher.launch(arrayOf("image/*"))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}