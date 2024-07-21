package com.mykotlinapps.bodybuilder.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.Preference
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.fragment.findNavController
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.ui.ItemsViewModel

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val viewModel: ItemsViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var preferencesContainer: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        loadingAnimation = view.findViewById(R.id.loading_animation)
        preferencesContainer = view.findViewById(R.id.preferences_container)

        // Show loading animation and hide preferences initially
        showLoadingAnimation()

        // Simulate loading duration
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoadingAnimation()
        }, 3000) // 3 seconds delay

        // Inflate the preferences
        childFragmentManager
            .beginTransaction()
            .replace(R.id.preferences_container, InnerSettingsFragment())
            .commit()

        return view
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Not used anymore
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        // Implement logic here if needed
    }

    private fun showLoadingAnimation() {
        loadingAnimation.visibility = View.VISIBLE
        preferencesContainer.visibility = View.GONE
    }

    private fun hideLoadingAnimation() {
        loadingAnimation.visibility = View.GONE
        preferencesContainer.visibility = View.VISIBLE
    }

    class InnerSettingsFragment : PreferenceFragmentCompat() {

        private lateinit var sharedPreferences: SharedPreferences
        private val viewModel: ItemsViewModel by viewModels()
        private lateinit var auth: FirebaseAuth

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            auth = FirebaseAuth.getInstance()

            // Set user email in preference summary
            val userEmailPref: Preference? = findPreference("user_email")
            userEmailPref?.summary = auth.currentUser?.email ?: "Not logged in"

            // Handle logout preference click
            val logoutPref: Preference? = findPreference("logout")
            logoutPref?.setOnPreferenceClickListener {
                auth.signOut()
                findNavController().navigate(R.id.signInFragment)
                true
            }

            sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == "weight" || key == "bodyFat" || key == "waistSize") {
                    updatePreferences()
                }
            }

            updatePreferences()

            viewModel.latestBodyStats.observe(this) { bodyStats ->
                bodyStats?.let {
                    findPreference<Preference>("weight")?.summary = it.weight.toString()
                    findPreference<Preference>("bodyFat")?.summary = it.bodyFat.toString()
                    findPreference<Preference>("waistSize")?.summary = it.waistSize.toString()
                }
            }
        }

        private fun updatePreferences() {
            val weight = sharedPreferences.getString("weight", "Not set")
            val bodyFat = sharedPreferences.getString("bodyFat", "Not set")
            val waistSize = sharedPreferences.getString("waistSize", "Not set")

            findPreference<Preference>("weight")?.summary = weight
            findPreference<Preference>("bodyFat")?.summary = bodyFat
            findPreference<Preference>("waistSize")?.summary = waistSize
        }
    }
}
