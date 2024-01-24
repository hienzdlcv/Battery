package com.example.battery.Api.ApiFromWallHeaven

import com.google.gson.annotations.SerializedName

class DataListWallHeaven {
}

//data Random
class DataHeaeven(
    @SerializedName("data") val data: List<Path>
)

class Path (
    @SerializedName("path") val url : String
)

//data Random Search
class DataHeaeven2(
    @SerializedName("data") val data: List<Path2>
)

class Path2 (
    @SerializedName("path") val url : String
)

//firebase
data class ImageModel1(
    val image : String
)

//autocreate
data class DataCate(
    val text : String
)