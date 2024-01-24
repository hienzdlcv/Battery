package com.example.battery.UI.PhotoPicker

data class ImageModel(
    val id: Long,
    val displayName: String?,
    val contentUri: String?,
    var isSelected: Boolean = false,
    var imagePath: String? = null,
    var path: String? = null
)

