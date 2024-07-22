package com.mykotlinapps.bodybuilder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mykotlinapps.bodybuilder.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.fragment.app.commit
import com.mykotlinapps.bodybuilder.ui.StrengthScoreView
import com.mykotlinapps.bodybuilder.ui.fragments.ExercisesDBFragment

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavigation()
        checkUserAuthentication()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    private fun checkUserAuthentication() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            // User is not logged in, navigate to signInFragment
            navController.navigate(R.id.signInFragment)
        } else {
            // User is logged in, navigate to homeFragment if currently at signInFragment
            if (navController.currentDestination?.id == R.id.signInFragment) {
                navController.navigate(R.id.homeFragment)
            }
        }

        binding.bottomNavigationView.setupWithNavController(navController)


    }
}
