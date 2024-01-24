package com.example.battery.Api.ApiFromWallHeaven

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiWallHeaven {
    @GET("api/v1/search")
    fun searchImages(
        @Query("q") query: String? = "",
        @Query("sorting") sorting: String? = "random",
        @Query("order") order: String? = "asc",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Call<DataHeaeven?>?

    @GET("api/v1/search")
    fun searchImages2(
        @Query("q") love: String?,
    ): Call<DataHeaeven2?>?
}