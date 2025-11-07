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
import androidx.lifecycle.ViewModelProvider
import com.example.photoalbumapp.Model.PhotoAlbum
import com.example.photoalbumapp.R
import com.example.photoalbumapp.databinding.ActivityUpdateImageBinding
import com.example.photoalbumapp.util.ConvertImage
import com.example.photoalbumapp.viewModel.PhotoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateImageActivity : AppCompatActivity() {

    lateinit var updateImageBinding: ActivityUpdateImageBinding
    var id = -1
    lateinit var viewModel: PhotoViewModel
    var imageAsString = ""

    lateinit var activityResultLauncherForSelectImage: ActivityResultLauncher<Intent>
    lateinit var selectImage : Bitmap
    var control = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        updateImageBinding = ActivityUpdateImageBinding.inflate(layoutInflater)

        setContentView(updateImageBinding.root)

        viewModel = ViewModelProvider(this)[PhotoViewModel::class.java]
        getAndSetdata()
        // register launchers
        registerActivityForSelectImage()

        updateImageBinding.imageViewUpdate.setOnClickListener {

            // Permission granted — open gallery
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncherForSelectImage.launch(intent)

        }
        updateImageBinding.buttonUpdate.setOnClickListener {

            updateImageBinding.buttonUpdate.text = "Updating... Please Wait"
            updateImageBinding.buttonUpdate.isEnabled = false

            GlobalScope.launch(Dispatchers.IO) {
                val updatedTitle = updateImageBinding.editTextupdateTittle.text.toString()
                val updatedDescription = updateImageBinding.editTextupdateDescription.text.toString()
                if (control) {
                    val newImageAsString = ConvertImage.convertToString(selectImage)
                    if (newImageAsString != null) {
                        imageAsString = newImageAsString
                    }
                    else {
                        Toast.makeText(applicationContext, "There is a problem, please select new image.", Toast.LENGTH_LONG).show()
                    }
                }

                val myUpdateImage = PhotoAlbum(updatedTitle, updatedDescription, imageAsString)
                myUpdateImage.imageId = id
                viewModel.update(myUpdateImage)
                finish()
            }


        }
        updateImageBinding.toolbarUpdateImage.setNavigationOnClickListener {
            finish()
        }

    }
    fun getAndSetdata() {
        id = intent.getIntExtra("id", -1)
        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val myImage = viewModel.getItemById(id)

                withContext(Dispatchers.IO) {
                    updateImageBinding.editTextupdateTittle.setText(myImage.imageTitle)
                    updateImageBinding.editTextupdateDescription.setText(myImage.imageDescription)
                    imageAsString = myImage.imageAsString
                    val imageAsBitmap = ConvertImage.convertToBitmap(imageAsString)
                    updateImageBinding.imageViewUpdate.setImageBitmap(imageAsBitmap)
                }

            }
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
                        updateImageBinding.imageViewUpdate.setImageBitmap(selectImage)
                        control = true
                    }
                }
            }
    }

}