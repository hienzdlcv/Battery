package com.example.battery.UI.PhotoPicker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.battery.Adapter.ImageAdapter
import com.example.battery.Base.BaseActivity
import com.example.battery.DataBase.MyEntity
import com.example.battery.R
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.databinding.ActivityPhotoPickerBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class PhotoPickerActivity : BaseActivity<ActivityPhotoPickerBinding>() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = mutableListOf<ImageModel>()
    private var path: String? = null
    private lateinit var galleryViewModel: GalleryViewModel



    override val layoutId: Int
        get() = R.layout.activity_photo_picker

    override fun setupUI() {
        super.setupUI()

        binding.rcvPhotoPicker.layoutManager = GridLayoutManager(this, 3)

        imageAdapter = ImageAdapter(imageList)
        binding.rcvPhotoPicker.adapter = imageAdapter

        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        imageAdapter.setOnItemClickListener(object : ImageAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedImage = imageList[position]
                selectedImage.isSelected = !selectedImage.isSelected
                Log.d("hh", "hehe: ${selectedImage.isSelected}")

                if (selectedImage.isSelected) {
                    path = selectedImage.contentUri
                    Log.d("hh", "hehe: ${path}")
                } else {
                    path = null
                }
                imageAdapter.notifyDataSetChanged()
            }
        })
        requestStoragePermission()
    }

    fun saveImageToRoom() {
        if (path != null) {
            val save = MyEntity(0, path)
            galleryViewModel.addItem(save)
        }
    }

    override fun setupListener() {
        super.setupListener()
        binding.apply {
            pickImageButton.setOnClickListener {
                saveImageToRoom()
            }
        }
    }


    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
        } else {
            loadImagesFromMediaStore()
        }
    }

    private fun loadImagesFromMediaStore() {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )

        val cursor: Cursor? = contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val displayNameColumn =
                it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val contentUriColumn =
                it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val displayName = it.getString(displayNameColumn)
                val contentUri =
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                        .appendPath(id.toString()).build().toString()

                val imageModel = ImageModel(id, displayName, contentUri)
                imageList.add(imageModel)
            }
        }
        imageAdapter.notifyDataSetChanged()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImagesFromMediaStore()
                } else {
                    Toast.makeText(this, "Access not Granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}