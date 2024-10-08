package com.mykotlinapps.bodybuilder.ui.fragments

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.Plan
import com.mykotlinapps.bodybuilder.data.PlansAdapter
import com.mykotlinapps.bodybuilder.data.Workout
import com.mykotlinapps.bodybuilder.data.adapter.RecentSessionsAdapter
import com.mykotlinapps.bodybuilder.databinding.BottomSheetAddWorkoutBinding
import com.mykotlinapps.bodybuilder.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null
    private var currentPhotoPath: String? = null
    private lateinit var loadingAnimation: LottieAnimationView

    private val workoutPlans = mapOf(
        "2023-07-19" to listOf(
            Plan("Workout A", "Description of Workout A"),
            Plan("Workout B", "Description of Workout B")
        ),
        "2023-07-20" to listOf(
            Plan("Workout C", "Description of Workout C"),
            Plan("Workout D", "Description of Workout D")
        )
    )

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            Log.d("HomeFragment", "Image capture successful")
            checkStoragePermissionAndSaveImage()
        } else {
            Toast.makeText(context, "Image capture failed", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestStoragePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            currentPhotoPath?.let { path -> addImageToGallery(path) }
        } else {
            Toast.makeText(context, "Storage permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingAnimation = binding.loadingAnimation
        binding.svgImageView.setSVG(R.raw.full_body_muscles)
        binding.apply {
            showLoadingAnimation()

            viewLifecycleOwner.lifecycleScope.launch {

                hideLoadingAnimation()
            }

            setupStrengthScoreView()
            setupCalendar()
            setupRecentSessions()
            setupAddWorkoutFab()
            setupPlansForDay()
        }
    }

    private fun setupStrengthScoreView() {
        val strengthScore = 278 // Example value
        binding.strengthScoreView.setScore(strengthScore)
        binding.strengthScoreView.setOnClickListener {
            navigateToAnalytics()
        }
    }

    private fun setupCalendar() {
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$year-${month + 1}-$dayOfMonth"
            val plans = workoutPlans[date] ?: emptyList()
            showPlansForDay(date, plans)
        }
    }

    private fun setupRecentSessions() {
        binding.recentSessionsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.recentSessionsRecyclerView.adapter = RecentSessionsAdapter(getRecentSessions())
    }

    private fun getRecentSessions(): List<Workout> {
        return listOf(
            Workout(name = "Morning Run", duration = "30 min", date =  Date()),
            Workout(name = "Evening Yoga", duration ="45 min", date = Date())
        )
    }

    private fun setupAddWorkoutFab() {
        binding.addWorkoutFab.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialog)
        val bottomSheetBinding = BottomSheetAddWorkoutBinding.inflate(layoutInflater)

        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        val parentLayout = bottomSheetBinding.root.parent as View
        val layoutParams = parentLayout.layoutParams

        layoutParams.width = resources.displayMetrics.widthPixels
        parentLayout.layoutParams = layoutParams

        bottomSheetBinding.bodyStatsOption.setOnClickListener {
            BodyStatsDialogFragment().show(childFragmentManager, "BodyStatsDialogFragment")
            bottomSheetDialog.dismiss()
        }
        bottomSheetBinding.plannedWorkoutOption.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_workoutPlanFragment)
            bottomSheetDialog.dismiss()
        }
        bottomSheetBinding.freestyleWorkoutOption.setOnClickListener {
            Toast.makeText(context, "freestyle", Toast.LENGTH_SHORT).show()
        }
        bottomSheetBinding.progressPhotoOption.setOnClickListener {
            checkCameraPermission()
        }

        bottomSheetDialog.show()
    }

    private fun navigateToAnalytics() {
        findNavController().navigate(R.id.action_homeFragment_to_analyticsFragment)
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(context, "Camera permission is needed to take a progress photo", Toast.LENGTH_SHORT).show()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        try {
            val imageFile = createImageFile()
            imageUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                imageFile
            )
            takePictureLauncher.launch(imageUri)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Could not create file", Toast.LENGTH_SHORT).show()
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
            Log.d("HomeFragment", "Image file created at $currentPhotoPath")
        }
    }

    private fun checkStoragePermissionAndSaveImage() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                currentPhotoPath?.let { path -> addImageToGallery(path) }
            }
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                Toast.makeText(context, "Storage permission is needed to save the photo", Toast.LENGTH_SHORT).show()
            }
            else -> {
                requestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun addImageToGallery(filePath: String) {
        val file = File(filePath)
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        val contentResolver = requireContext().contentResolver
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        if (uri != null) {
            try {
                val inputStream: InputStream = file.inputStream()
                val outputStream = contentResolver.openOutputStream(uri)
                inputStream.copyTo(outputStream!!)
                inputStream.close()
                outputStream.close()
                Log.d("HomeFragment", "Image added to gallery: $uri")
                Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Log.e("HomeFragment", "Failed to save image to gallery", e)
            }
        } else {
            Log.e("HomeFragment", "Failed to add image to gallery")
        }
    }

    private fun showPlansForDay(date: String, plans: List<Plan>) {
        binding.title.text = "Plans for $date"
        binding.plansRecyclerView.adapter = PlansAdapter(plans)
        binding.plansForDayLayout.visibility = View.VISIBLE
    }

    private fun setupPlansForDay() {
        binding.btnHide.setOnClickListener {
            binding.plansForDayLayout.visibility = View.GONE
        }
    }

    private fun showLoadingAnimation() {
        binding.loadingAnimation.visibility = View.VISIBLE
        binding.svgImageView.visibility = View.VISIBLE
        binding.fragmentContent.visibility = View.GONE
    }

    private fun hideLoadingAnimation() {
        binding.loadingAnimation.visibility = View.GONE
        binding.fragmentContent.visibility = View.VISIBLE
        binding.svgImageView.visibility = View.GONE
    }
}
