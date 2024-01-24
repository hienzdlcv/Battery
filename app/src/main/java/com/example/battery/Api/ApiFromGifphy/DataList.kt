package com.example.battery.Api.ApiFromGifphy

import com.google.gson.annotations.SerializedName

//treding gifs
data class DataResult(
    @SerializedName("data") val res: List<DataObject>
)

data class DataObject(
    @SerializedName("images") val images: DataImage
)

data class DataImage(
    @SerializedName("original") val ogImage: ogImage
)

data class ogImage(
    @SerializedName("url") val url: String
)




//parent cate
data class DataCate(
    @SerializedName("data") val data: List<NameCate>
)

data class NameCate(
    val name: String,
    @SerializedName("name_encoded") val nameEncoded: String
)



//sub category
data class DataSubCate(
    @SerializedName("data") val subData: List<SubNameCate>
)

data class SubNameCate(
    @SerializedName("name_encoded") val nameEncoded: String,
    @SerializedName("name") val name: String
)

//sub gif
data class DataSubGif(
    @SerializedName("data") val subGif: List<Gif>
)

data class Gif(
    @SerializedName("images") val images: GifImages
)

data class GifImages(
    @SerializedName("original") val still480w: GifImage
)

data class GifImage(
    @SerializedName("url") val url: String
)


//random gif
class DataRandomGif(
    @SerializedName("data") val data: Data1
)

data class Data1(
    @SerializedName("images") val images: Data2
)

data class Data2(
    @SerializedName("original") val ogImage: Data3
)

data class Data3(
    @SerializedName("url") val url: String
)

//anime gif
class DataAnime(
    @SerializedName("data") val data: List<ImageAnime>
)

class ImageAnime(
    @SerializedName("images") val images: OriginalAnime
)

class OriginalAnime(
    @SerializedName("original") val ogImage: UrlAnime
)

class UrlAnime(
    @SerializedName("url") val url: String
)


data class Language(
    val id: Int, val name: String, val code: String, val image: Int
)

//trending sticker
data class Data01(
    @SerializedName("data") val data: List<DataImg>
)

data class DataImg(
    @SerializedName("images") val image : DataOrignal
)

data class DataOrignal (
    @SerializedName("original") val dataOri : urlImg
)

data class urlImg (
    @SerializedName("url") val url : String
)

