package com.example.battery.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.battery.Api.ApiFromGifphy.Data01
import com.example.battery.Api.ApiFromGifphy.Data1
import com.example.battery.Api.ApiFromGifphy.DataAnime
import com.example.battery.Api.ApiFromGifphy.DataCate
import com.example.battery.Api.ApiFromGifphy.DataImage
import com.example.battery.Api.ApiFromGifphy.DataImg
import com.example.battery.DataBase.MyEntity
import com.example.battery.DataBase.MyRepository
import com.example.battery.DataBase.RoomDB
import com.example.battery.Api.ApiFromGifphy.DataObject
import com.example.battery.Api.ApiFromGifphy.DataRandomGif
import com.example.battery.Api.ApiFromGifphy.DataResult
import com.example.battery.Api.ApiFromGifphy.DataSubCate
import com.example.battery.Api.ApiFromGifphy.DataSubGif
import com.example.battery.Api.ApiFromGifphy.Gif
import com.example.battery.Api.ApiFromGifphy.ImageAnime
import com.example.battery.Api.ApiFromGifphy.NameCate
import com.example.battery.Api.ApiFromGifphy.SubNameCate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class GalleryViewModel(application: Application) : AndroidViewModel(application) {


    //Room Logic
    private val repository: MyRepository
    val readAllData: LiveData<List<MyEntity>>

    private val _chosenPath = MutableLiveData<String?>()
    private val _getAll1 = MutableLiveData<LiveData<List<MyEntity>>>()
    private val _choosenSticker = MutableLiveData<String?>()
    private val _readAllSticker = MutableLiveData<LiveData<List<MyEntity>>>()
    val getAll1 : LiveData<LiveData<List<MyEntity>>> get() = _getAll1
    val readAllSticker : LiveData<LiveData<List<MyEntity>>> get() = _readAllSticker
    val chosenPath: MutableLiveData<String?> get() = _chosenPath
    val choosenSticker : MutableLiveData<String?> get() = _choosenSticker


    init {
        val uDao = RoomDB.getInstance(application).getItemDao()
        repository = MyRepository(uDao)
        readAllData = repository.readAllData
    }

    fun addItem(neto: MyEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(neto)
        }
    }

    fun updateIsChoosen(entityPath: String) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateIsChoosen(entityPath)
        }
    }

    fun updateIsChoosenPath(sticker: String) {
        viewModelScope.launch (Dispatchers.IO){
            repository.updateIsChoosenSticker(sticker)
        }
    }

    fun deleteSelectedItem(list: ArrayList<MyEntity>) {
        val selectedItem = list.filter { it.isSelected }
        if (selectedItem.isNotEmpty()) {
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    repository.deleteSelectedItem(selectedItem)
                }
            }
        }
    }

    fun deleteChoosenSticker(list: ArrayList<MyEntity>) {
        val selectedSticker = list.filter { it.isChoosen }
        if(selectedSticker.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteChoosenSticker(selectedSticker)
            }
        }
    }

    fun theChoosenOne() {
        viewModelScope.launch(Dispatchers.IO) {
            val chosenPath = repository.theChoosenOne()
            _chosenPath.postValue(chosenPath)
        }
    }

    fun theChoosenSticker() {
        viewModelScope.launch(Dispatchers.IO) {
            val choosenSticker = repository.theChoosenSticker()
            _choosenSticker.postValue(choosenSticker)
        }
    }

    fun fetchStickerData() {
        viewModelScope.launch(Dispatchers.IO) {
            val readAllSticker = repository.readStickerData()
            _readAllSticker.postValue(readAllSticker)
        }
    }

    fun getAll1() {
        viewModelScope.launch(Dispatchers.IO) {
            val getAll = repository.getAll1()
            _getAll1.postValue(getAll)
        }
    }


    //api Logic

    private val _listtrendingGifs = MutableLiveData<List<DataObject>>()
    private val _listCate = MutableLiveData<List<NameCate>>()
    private val _listSubCate = MutableLiveData<List<SubNameCate>>()
    private val _listSubGif = MutableLiveData<List<Gif>>()
    private val _listRandomGif = MutableLiveData<List<DataImg>>()
    private val _listAnimeGif = MutableLiveData<List<ImageAnime>>()
    val listGifs: LiveData<List<DataObject>> get() = _listtrendingGifs
    val listCate: LiveData<List<NameCate>> get() = _listCate
    val listSubCate: LiveData<List<SubNameCate>> get() = _listSubCate
    val listSubGif: LiveData<List<Gif>> get() = _listSubGif
    val listRandomGif : LiveData<List<DataImg>> get() = _listRandomGif
    val listAnimeGif : LiveData<List<ImageAnime>> get() = _listAnimeGif

    fun fetchTrendingData() {
        repository.createApi().getTrendingGif().enqueue(object : retrofit2.Callback<DataResult?> {
            override fun onResponse(call: Call<DataResult?>, response: Response<DataResult?>) {
                val body = response.body()
                if (body != null) {
                    _listtrendingGifs.postValue(body.res)
                }
            }

            override fun onFailure(call: Call<DataResult?>, t: Throwable) {
                Log.e("9999", "onFailure: $t", )
            }

        })
    }

    fun fetchAnime(love: String) {
        repository.createApi().searchGifs("6jmfqLkiHH2TJeoImTeuzVHt4p7KFCPD",love).enqueue(object : retrofit2.Callback<DataAnime?>{
            override fun onResponse(call: Call<DataAnime?>, response: Response<DataAnime?>) {
                val body = response.body()
                if(body!=null) {
                    _listAnimeGif.postValue(body.data)
                }
            }

            override fun onFailure(call: Call<DataAnime?>, t: Throwable) {
                Log.e("9999", "onFailure: $t", )
            }

        })
    }

    fun fetchCateGory() {
        repository.createApi().getList().enqueue(object : retrofit2.Callback<DataCate> {
            override fun onResponse(call: Call<DataCate>, response: Response<DataCate>) {
                val body = response.body()
                if (body != null) {
                    _listCate.postValue(body.data)
                }
            }

            override fun onFailure(call: Call<DataCate>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchSubCategory(love: String) {
        repository.createApi().getSubList(love).enqueue(object : retrofit2.Callback<DataSubCate> {
            override fun onResponse(call: Call<DataSubCate>, response: Response<DataSubCate>) {
                val body = response.body()
                if (body != null) {
                    _listSubCate.postValue(body.subData)
                }
            }

            override fun onFailure(call: Call<DataSubCate>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchSubGif(love: String) {
        repository.createApi().getGifsForSubcategory(love).enqueue(object : retrofit2.Callback<DataSubGif> {
                override fun onResponse(call: Call<DataSubGif>, response: Response<DataSubGif>) {
                    val body = response.body()
                    if (body != null) {
                        _listSubGif.postValue(body.subGif)
                    }
                }

                override fun onFailure(call: Call<DataSubGif>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun fetchSticker() {
        repository.createApi().getSticker().enqueue(object : retrofit2.Callback<Data01> {
            override fun onResponse(call: Call<Data01>, response: Response<Data01>) {
                val body = response.body()
                if(body != null) {
                    _listRandomGif.postValue(body.data)
                }
            }

            override fun onFailure(call: Call<Data01>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}