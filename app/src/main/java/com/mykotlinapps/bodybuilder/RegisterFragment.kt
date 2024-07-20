package com.mykotlinapps.bodybuilder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.snackbar.Snackbar
import com.mykotlinapps.bodybuilder.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()
            val height = binding.etHeight.text.toString()
            val weight = binding.etWeight.text.toString()
            val gender = if (binding.rbMale.isChecked) "Man" else "Woman"
            val age = binding.etAge.text.toString()

            // Hide the keyboard
            hideKeyboard(view)

            // Validate inputs
            if (password != repeatPassword) {
                Snackbar.make(binding.root, "Passwords do not match", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (fullName.isBlank() || email.isBlank() || password.isBlank() || height.isBlank() || weight.isBlank() || age.isBlank()) {
                Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            registerUser(fullName, email, password, height, weight, gender, age.toInt())
        }

        binding.btnGoToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_signInFragment)
        }
    }

    private fun registerUser(fullName: String, email: String, password: String, height: String, weight: String, gender: String, age: Int) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("RegisterFragment", "User registration successful")
                    // Update user profile
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = fullName
                    }
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileUpdateTask ->
                            if (profileUpdateTask.isSuccessful) {
                                Log.d("RegisterFragment", "User profile updated")
                                // Store user data in Firestore
                                val userDetails = UserDetails(
                                    fullName = fullName,
                                    email = email,
                                    heightInCm = height.toInt(),
                                    weightInKg = weight.toInt(),
                                    gender = gender,
                                    age = age
                                )
                                val userData = User(userDetails = userDetails)
                                db.collection("users").document(user!!.uid)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        Log.d("RegisterFragment", "User data saved to Firestore")
                                        // Navigate to Home fragment after successful registration and data storage
                                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("RegisterFragment", "Failed to save user data: ${e.message}")
                                        Snackbar.make(binding.root, "Failed to save user data: ${e.message}", Snackbar.LENGTH_LONG).show()
                                    }
                            } else {
                                Log.e("RegisterFragment", "Failed to update profile: ${profileUpdateTask.exception?.message}")
                                Snackbar.make(binding.root, "Failed to update profile: ${profileUpdateTask.exception?.message}", Snackbar.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Log.e("RegisterFragment", "Registration failed: ${task.exception?.message}")
                    Snackbar.make(binding.root, "Registration failed: ${task.exception?.message}", Snackbar.LENGTH_LONG).show()
                }
            }
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
