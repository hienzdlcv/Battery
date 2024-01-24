package com.example.battery.DataBase

import androidx.lifecycle.LiveData
import com.example.battery.Api.ApiFromWallHeaven.ApiWallHeaven
import com.example.battery.Api.ApiFromGifphy.ApiHome
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRepository(val dao: MyDAO) {

    //api WallHeaven
    private val BaseUrl1 = "https://wallhaven.cc/"

    fun createApi1(): ApiWallHeaven {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(ApiWallHeaven::class.java)
        return retrofitService
    }

    //api Gifphy
    private val BaseUrl = "https://api.giphy.com/v1/"
    private val api = "6jmfqLkiHH2TJeoImTeuzVHt4p7KFCPD"

    fun createApi(): ApiHome {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(ApiHome::class.java)
        return retrofitService
    }

    //room
    val readAllData: LiveData<List<MyEntity>> = dao.getAll()


    suspend fun addItem(entity: MyEntity) {
        dao.insert(entity)
    }
    fun deleteSelectedItem(entity: List<MyEntity>) {
        dao.deleteSelectedItem(entity)
    }

    fun deleteChoosenSticker(entity: List<MyEntity>) {
        dao.deleteChoosenSticker(entity)
    }

    suspend fun updateIsChoosen(entityPath: String) {
        dao.updateIsChoosen(entityPath)
    }

    suspend fun updateIsChoosenSticker(stickerPath : String) {
        dao.updateisChoosenSticker(stickerPath)
    }

    suspend fun theChoosenOne(): String? {
        return dao.theChoosenOne()
    }

    suspend fun theChoosenSticker(): String? {
        return dao.theChoosenSticker()
    }

    suspend fun readStickerData(): LiveData<List<MyEntity>> {
        return dao.getAllSticker()
    }

    suspend fun getAll1() : LiveData<List<MyEntity>> {
        return dao.getAll1()
    }

    //api from wall heaven

}