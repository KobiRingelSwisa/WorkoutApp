//package com.mykotlinapps.bodybuilder.ui.workout
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.SearchView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.mykotlinapps.bodybuilder.data.Exercise
//import com.mykotlinapps.bodybuilder.ExercisesAdapter
//import com.mykotlinapps.bodybuilder.databinding.FragmentWorkoutBinding
//
//class WorkoutFragment : Fragment() {
//
//    private var _binding: FragmentWorkoutBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var exercisesAdapter: ExercisesAdapter
//    private val exercises = ArrayList<Exercise>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        exercisesAdapter = ExercisesAdapter(exercises)
//        binding.recyclerViewExercises.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = exercisesAdapter
//        }
//
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // Handle search query
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // Handle search text change
//                return false
//            }
//        })
//
//        binding.logNextSetButton.setOnClickListener {
//            // Handle logging the next set
//        }
//
//        // Implement swipe to delete
//        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                exercisesAdapter.removeExercise(viewHolder.adapterPosition)
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
//        itemTouchHelper.attachToRecyclerView(binding.recyclerViewExercises)
//
//        // Implement drag and drop to reorder
//        val dragCallback = object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//            0
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                exercisesAdapter.moveExercise(viewHolder.adapterPosition, target.adapterPosition)
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
//        }
//        val dragItemTouchHelper = ItemTouchHelper(dragCallback)
//        dragItemTouchHelper.attachToRecyclerView(binding.recyclerViewExercises)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
