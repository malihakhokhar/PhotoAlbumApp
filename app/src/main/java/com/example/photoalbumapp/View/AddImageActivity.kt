package com.example.photoalbumapp.View

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.photoalbumapp.databinding.ActivityAddImageBinding
import com.example.photoalbumapp.util.ControlPermission
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.photoalbumapp.Model.PhotoAlbum
import com.example.photoalbumapp.util.ConvertImage
import com.example.photoalbumapp.viewModel.PhotoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddImageActivity : AppCompatActivity() {

    lateinit var addImageBinding: ActivityAddImageBinding
    lateinit var activityResultLauncherForSelectImage: ActivityResultLauncher<Intent>
    lateinit var activityResultLauncherForPermission: ActivityResultLauncher<String>
    lateinit var selectImage: Bitmap
    lateinit var photoViewModel : PhotoViewModel
    var control = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        addImageBinding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(addImageBinding.root)

        photoViewModel = ViewModelProvider(this)[PhotoViewModel::class.java]

        // register launchers
        registerActivityForSelectImage()
        registerActivityForPermission()

        addImageBinding.imageViewAdd.setOnClickListener {
            // Check permission using your utility class
            if (ControlPermission.checkPermission(this)) {
                // Permission granted — open gallery
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncherForSelectImage.launch(intent)
            } else {
                // Permission not granted — request it using modern launcher
                val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }
                activityResultLauncherForPermission.launch(permissionToRequest)
            }
        }

        addImageBinding.buttonAdd.setOnClickListener {
            // Your button logic
            if (control) {
                addImageBinding.buttonAdd.text = "Uploading... Please Wait"
                addImageBinding.buttonAdd.isEnabled = false

                GlobalScope.launch(Dispatchers.IO) {
                    val title = addImageBinding.editTextAddTittle.text.toString()
                    val description = addImageBinding.editTextAddDescription.text.toString()
                    val imageAsString = ConvertImage.convertToString(selectImage)
                    if (imageAsString != null) {
                        photoViewModel.insert(PhotoAlbum(title, description, imageAsString))
                        control = false
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "There is a problem, please select new image.", Toast.LENGTH_LONG).show()
                    }
                }


            } else {
                Toast.makeText(applicationContext, "Please select a photo.", Toast.LENGTH_LONG).show()

            }


        }

        addImageBinding.toolbarAddImage.setNavigationOnClickListener {
            finish()
        }
    }

    private fun registerActivityForSelectImage() {
        activityResultLauncherForSelectImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val resultCode = result.resultCode
                val imageData = result.data
                if (resultCode == RESULT_OK && imageData != null) {
                    val imageUri = imageData.data
                    imageUri?.let {
                        selectImage = if (Build.VERSION.SDK_INT >= 28) {
                            val imageSource = ImageDecoder.createSource(this.contentResolver, it)
                            ImageDecoder.decodeBitmap(imageSource)
                        } else {
                            MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        }
                        addImageBinding.imageViewAdd.setImageBitmap(selectImage)
                        control = true
                    }
                }
            }
    }

    private fun registerActivityForPermission() {
        activityResultLauncherForPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    // Permission granted — open gallery
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncherForSelectImage.launch(intent)
                } else {
                    // Permission denied — check if permanently denied
                    val shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                            Manifest.permission.READ_MEDIA_IMAGES
                        else
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    )

                    if (shouldShow) {
                        // User denied but didn't select "Don't ask again"
                        Toast.makeText(this, "Permission is required to access photos.", Toast.LENGTH_LONG).show()
                    } else {
                        // User permanently denied (checked "Don't ask again")
                        Toast.makeText(this, "Please enable permission from Settings.", Toast.LENGTH_LONG).show()

                        // Optionally open settings directly:
                        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = android.net.Uri.parse("package:$packageName")
                        startActivity(intent)
                    }
                }
            }
    }

}
