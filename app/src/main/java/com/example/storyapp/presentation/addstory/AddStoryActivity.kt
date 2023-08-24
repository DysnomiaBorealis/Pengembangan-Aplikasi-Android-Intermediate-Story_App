package com.example.storyapp.presentation.addstory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storyapp.R
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.storyapp.databinding.ActivityAddStoryBinding
import com.example.storyapp.presentation.Locator.Locator
import com.example.storyapp.presentation.utils.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.io.File

class AddStoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddStoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<AddStoryViewModel>(factoryProducer = { Locator.addStoryViewModelFactory })
    private lateinit var currentPhotoPath: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latLng: LatLng? = null
    private var getFile: File? = null
    private var selectedImageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.btGallery.setOnClickListener { startGallery() }
        binding.btCamera.setOnClickListener { startTakePhoto() }

        binding.switchLocation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                getMyLastLocation()
            }
        }


        binding.btAddStory.setOnClickListener {
            selectedImageFile?.let { file ->
                viewModel.addStory(file.reduceFileImage(), binding.edAddDescription.text.toString(),
                    latLng)
            } ?: run {
                Toast.makeText(this, getString(R.string.please_choose_image), Toast.LENGTH_SHORT).show()
            }
        }

        if (allPermissionsGranted()) {
            binding.btCamera.setOnClickListener { startTakePhoto() }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_CAMERA_PERMISSIONS, REQUEST_CODE_CAMERA_PERMISSION)
        }

        viewModel.addStoryState.launchAndCollectIn(this) { state ->
            when (state.resultAddStory) {
                is ResultState.Success<String> -> {
                    binding.pbAddStory.visibility = View.GONE  // hide progress bar
                    finish()
                }
                is ResultState.Loading -> binding.pbAddStory.visibility = View.VISIBLE  // show progress bar
                is ResultState.Error -> {
                    binding.pbAddStory.visibility = View.GONE  // hide progress bar
                }
                else -> Unit
            }
        }


    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startTakePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                createCustomTempFile(application).also { file ->
                    val photoURI: Uri = FileProvider.getUriForFile(this, packageName, file)
                    currentPhotoPath = file.absolutePath
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    launcherIntentCamera.launch(intent)
                }
            }
        }
    }

    private fun startGallery() {
        Intent().also { intent ->
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "Choose a Picture")
            launcherIntentGallery.launch(chooser)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.ivPreviewPhoto.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val imageFile = selectedImg.uriToFile(this)
            selectedImageFile = imageFile
            binding.ivPreviewPhoto.setImageURI(selectedImg)
        }
    }

    companion object {
        private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_CAMERA_PERMISSION = 10
        private val REQUIRED_LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val REQUEST_CODE_LOCATION_PERMISSIONS = 11
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (!REQUIRED_CAMERA_PERMISSIONS.checkPermissionsGranted(baseContext)) {
                Toast.makeText(
                    this, getString(R.string.cant_get_camera_permission), Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        } else if (requestCode == REQUEST_CODE_LOCATION_PERMISSIONS) {
            if (!REQUIRED_LOCATION_PERMISSIONS.checkPermissionsGranted(baseContext)) {
                Toast.makeText(
                    this, getString(R.string.cant_get_location_permission), Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun getMyLastLocation() {
        if (REQUIRED_LOCATION_PERMISSIONS.checkPermissionsGranted(baseContext)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    latLng = LatLng(location.latitude, location.longitude)
                } else {
                    binding.switchLocation.isEnabled = false
                    Toast.makeText(
                        this@AddStoryActivity,
                        getString(R.string.location_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                // Precise location access granted.
                getMyLastLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                // Only approximate location access granted.
                getMyLastLocation()
            }

            else -> {
                // No location access granted.
            }
        }
    }



    private fun allPermissionsGranted() = REQUIRED_CAMERA_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }



}


