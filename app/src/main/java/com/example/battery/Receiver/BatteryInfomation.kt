package com.example.battery.Receiver

import kotlinx.coroutines.flow.MutableStateFlow

object BatteryInfomation {
    val batteryPercentage = MutableStateFlow(0)
    val batteryVoltage = MutableStateFlow(0f)
    val batteryHealth = MutableStateFlow("")
    val batteryTemperature = MutableStateFlow(0f)
    val batteryType = MutableStateFlow("")
    val batteryCapacity = MutableStateFlow("")

    val chargeStatus1 = MutableStateFlow("")

    val timeColor = MutableStateFlow("#FFFFFF")
    val timeFont = MutableStateFlow("roboto")
    val mLayout = MutableStateFlow(1)
    val showSticker = MutableStateFlow(false)
    val timeShow = MutableStateFlow(false)
    val dateShow = MutableStateFlow(false)

    val textSearch = MutableStateFlow("")
}