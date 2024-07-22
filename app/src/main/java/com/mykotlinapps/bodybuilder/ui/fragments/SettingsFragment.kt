package com.mykotlinapps.bodybuilder.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.viewmodel.ItemsViewModel
import java.io.File
import java.io.FileWriter
import java.io.IOException

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val viewModel: ItemsViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var preferencesContainer: View
    private lateinit var auth: FirebaseAuth

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

            // Log current user to console
            val currentUser = auth.currentUser
            if (currentUser != null) {
                Log.d("SettingsFragment", "Current User: $currentUser")
                Log.d("SettingsFragment", "User ID: ${currentUser.uid}")
                Log.d("SettingsFragment", "User Email: ${currentUser.email}")
            } else {
                Log.d("SettingsFragment", "No user is currently logged in.")
            }

            // Set user email in preference summary
            val userEmailPref: Preference? = findPreference("user_email")
            userEmailPref?.summary = auth.currentUser?.email ?: "Not logged in"

            // Set user full name in preference summary
            val userFullNamePref: Preference? = findPreference("user_full_name")
            val userId = auth.currentUser?.uid
            if (userId != null) {
                FirebaseFirestore.getInstance().collection("users").document(userId)
                    .get()
                    .addOnSuccessListener { document ->
//                        sdcard android data com files document file
                        val userDetails = document.get("userDetails") as? Map<String, Any>
                        val fullName = auth.currentUser?.displayName ?: "Unknown"
                        userFullNamePref?.summary = fullName
                        Log.d("SettingsFragment", "Fetched Full Name: $fullName")
                    }
                    .addOnFailureListener {
                        userFullNamePref?.summary = "Error fetching name"
                        Log.e("SettingsFragment", "Error fetching full name", it)
                    }
            }

            // Handle logout preference click
            val logoutPref: Preference? = findPreference("logout")
            logoutPref?.setOnPreferenceClickListener {
                auth.signOut()
                findNavController().navigate(R.id.signInFragment)
                true
            }

            // Handle delete account preference click
            val deleteAccountPref: Preference? = findPreference("delete_account")
            deleteAccountPref?.setOnPreferenceClickListener {
                showPasswordPrompt()
                true
            }

            // Handle export data preference click
            val exportDataPref: Preference? = findPreference("export_data")
            exportDataPref?.setOnPreferenceClickListener {
                exportUserData()
                true
            }
        }

        private fun showPasswordPrompt() {
            val passwordInput = EditText(requireContext())
            passwordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

            AlertDialog.Builder(requireContext())
                .setTitle("Confirm Password")
                .setMessage("Please enter your password to delete your account.")
                .setView(passwordInput)
                .setPositiveButton("Confirm") { _, _ ->
                    val password = passwordInput.text.toString()
                    reAuthenticateAndDeleteUser(password)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        private fun reAuthenticateAndDeleteUser(password: String) {
            val user = auth.currentUser
            user?.let {
                val email = it.email

                if (email != null && password.isNotEmpty()) {
                    val credential = EmailAuthProvider.getCredential(email, password)
                    user.reauthenticate(credential)
                        .addOnCompleteListener { reauthTask ->
                            if (reauthTask.isSuccessful) {
                                deleteUserAccount()
                            } else {
                                Toast.makeText(context, "Re-authentication failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Email or password is missing", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun deleteUserAccount() {
            val user = auth.currentUser
            user?.let {
                val userId = it.uid
                FirebaseFirestore.getInstance().collection("users").document(userId)
                    .delete()
                    .addOnSuccessListener {
                        // Now delete the user authentication account
                        user.delete()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    findNavController().navigate(R.id.signInFragment)
                                    Toast.makeText(context, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Failed to delete account", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to delete account data", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        private fun exportUserData() {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                FirebaseFirestore.getInstance().collection("users").document(userId)
                    .get()
                    .addOnSuccessListener { document ->
                        val userData = document.data
                        if (userData != null) {
                            val jsonString = GsonBuilder().setPrettyPrinting().create().toJson(userData)
                            saveJsonToFile(jsonString)
                        } else {
                            Toast.makeText(context, "No user data found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        private fun saveJsonToFile(jsonString: String) {
            val fileName = "user_data_${System.currentTimeMillis()}.json"
            val file = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
            try {
                FileWriter(file).use { it.write(jsonString) }
                Toast.makeText(context, "Data exported to $fileName", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(context, "Failed to export data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
