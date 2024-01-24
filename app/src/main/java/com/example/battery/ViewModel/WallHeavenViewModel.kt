package com.example.battery.ViewModel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.battery.Api.ApiFromGifphy.NameCate
import com.example.battery.Api.ApiFromWallHeaven.DataHeaeven
import com.example.battery.Api.ApiFromWallHeaven.DataHeaeven2
import com.example.battery.Api.ApiFromWallHeaven.Path
import com.example.battery.Api.ApiFromWallHeaven.Path2
import com.example.battery.DataBase.MyRepository
import com.example.battery.DataBase.RoomDB
import com.example.battery.MainActivity
import com.example.battery.Receiver.BatteryInfomation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Timer
import java.util.TimerTask

@RequiresApi(Build.VERSION_CODES.O)
class WallHeavenViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: MyRepository

    private val _timeNow = MutableStateFlow<String>("")
    val timeNow = _timeNow.asStateFlow()

    private val _timeNow1 = MutableStateFlow<String>("")
    val timeNow1 = _timeNow1.asStateFlow()

    private val _timeNow2 = MutableStateFlow<String>("")
    val timeNow2 = _timeNow2.asStateFlow()


    private val timerScope = CoroutineScope(Dispatchers.Default)

    private val _listCate = MutableLiveData<List<Path>>()
    val listCate: LiveData<List<Path>> get() = _listCate

    init {
        when(BatteryInfomation.mLayout.value){
            1->startPeriodicUpdates()
            2->startPeriodicUpdates2()
            else -> startPeriodicUpdates3()
        }
        val uDao = RoomDB.getInstance(application).getItemDao()
        repository = MyRepository(uDao)
    }

    private fun startPeriodicUpdates() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateTimeNow()
            }
        }, 0, 60000)
    }

    private fun startPeriodicUpdates2() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateTimeNow1()
            }
        }, 0, 60000)
    }

    private fun startPeriodicUpdates3() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateTimeNow2()
            }
        }, 0, 60000)
    }

    fun updateTimeNow() {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = currentTime.format(formatter)
        _timeNow.value = formattedTime
    }

    fun updateTimeNow1() {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH\nmm")
        val formattedTime = currentTime.format(formatter)
        _timeNow1.value = formattedTime
    }

    fun updateTimeNow2() {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = currentTime.format(formatter)
        _timeNow2.value = formattedTime
    }

    override fun onCleared() {
        timerScope.cancel()
        super.onCleared()
    }
}