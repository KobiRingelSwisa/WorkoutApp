package com.mykotlinapps.bodybuilder.ui.all_character

import android.app.AlertDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.databinding.AllItemsLayoutBinding
import com.mykotlinapps.bodybuilder.ui.ItemsViewModel

class AllItemsFragment : Fragment() {

    private  var _binding : AllItemsLayoutBinding? = null
    private  val binding get() = _binding !!

    private  val viewModel : ItemsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = AllItemsLayoutBinding.inflate(inflater,container,false)
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("title")?.let{
            Toast.makeText(requireActivity(),it,Toast.LENGTH_SHORT).show()
        }
        viewModel.items?.observe(viewLifecycleOwner) {
            binding.recycler.adapter = ItemAdapter(it,object: ItemAdapter.ItemListener {
                override fun onItemClicked(index: Int) {
                    Toast.makeText(requireContext(),"${it[index]}", Toast.LENGTH_SHORT).show()
                }

                override fun onItemLongClicked(index: Int) {
                    val item = it[index]
                    viewModel.setItem(item)
                    findNavController().navigate(R.id.action_allItemsFragment_to_detailItemFragment)
                }
            })
            binding.recycler.layoutManager = GridLayoutManager(requireContext(),2)
        }                                    // linearLayoutManger


        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) = makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val item = (binding.recycler.adapter as ItemAdapter).itemAt(viewHolder.adapterPosition)
                viewModel.deleteItem(item)
                //ItemManager.remove(viewHolder.adapterPosition)
                //binding.recycler.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binding.recycler)
    }
    @Suppress
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    @Suppress
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_delete){
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Confirm Delete").setMessage("Are you sure you want to delete all items?")
                .setPositiveButton("Yes"
                ) { dialog, which ->
                    viewModel.deleteAll()
                    Toast.makeText(requireContext(),"Items Deleted",Toast.LENGTH_SHORT).show()
                }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}