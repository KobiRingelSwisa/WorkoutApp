package com.mykotlinapps.bodybuilder

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.airbnb.lottie.LottieAnimationView

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mykotlinapps.bodybuilder.databinding.FragmentPlansBinding
class PlansFragment : Fragment() {

    private var _binding: FragmentPlansBinding? = null
    private val binding get() = _binding!!
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var fragmentContent: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingAnimation = binding.root.findViewById(R.id.loading_animation)
        fragmentContent = binding.root.findViewById(R.id.fragment_content)

        // Show loading animation and hide content initially
        showLoadingAnimation()

        // Simulate loading duration
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoadingAnimation()
        }, 3000) // 3 seconds delay

        // TODO: Setup RecyclerView for workout templates
        // TODO: Implement AI-generated template functionality
        // TODO: Implement community-shared workouts functionality


    }

//    private fun navigateToCreateWorkout() {
//        // Replace with your navigation logic
//        // For example, if you are using Navigation Component:
//        findNavController().navigate(R.id.action_plansFragment_to_fragment_create_workout)
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoadingAnimation() {
        loadingAnimation.visibility = View.VISIBLE
        fragmentContent.visibility = View.GONE
    }

    private fun hideLoadingAnimation() {
        loadingAnimation.visibility = View.GONE
        fragmentContent.visibility = View.VISIBLE
    }
}