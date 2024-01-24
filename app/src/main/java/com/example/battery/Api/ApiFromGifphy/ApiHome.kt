package com.example.battery.Api.ApiFromGifphy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiHome {
    @GET("gifs/trending?api_key=6jmfqLkiHH2TJeoImTeuzVHt4p7KFCPD")
    fun getTrendingGif(): retrofit2.Call<DataResult>

    @GET("gifs/categories?api_key=6jmfqLkiHH2TJeoImTeuzVHt4p7KFCPD")
    fun getList(): retrofit2.Call<DataCate>

    @GET("gifs/categories/{love}?api_key=6jmfqLkiHH2TJeoImTeuzVHt4p7KFCPD")
    fun getSubList(@Path("love") love: String): retrofit2.Call<DataSubCate>

    @GET("gifs/categories/subcategories/{love}?api_key=6jmfqLkiHH2TJeoImTeuzVHt4p7KFCPD")
    fun getGifsForSubcategory(@Path("love") love: String): retrofit2.Call<DataSubGif>

    @GET("gifs/random?api_key=6jmfqLkiHH2TJeoImTeuzVHt4p7KFCPD&tag=&rating=g")
    fun getRandomGif(): retrofit2.Call<DataRandomGif>

    @GET("stickers/trending?api_key=6jmfqLkiHH2TJeoImTeuzVHt4p7KFCPD&limit=25&offset=0&rating=g&bundle=messaging_non_clips")
    fun getSticker(): retrofit2.Call<Data01>


    @GET("gifs/search")
    fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") love: String,
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = "g",
        @Query("lang") lang: String = "en",
        @Query("bundle") bundle: String = "messaging_non_clips"
    ): Call<DataAnime>
}