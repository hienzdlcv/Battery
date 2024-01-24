package com.example.battery.Receiver

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.battery.Base.BasePrefers
import com.example.battery.R
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BatteryChargingReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> {
                val percentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 50)
                val ExtraVoltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0).toFloat()
                val voltage = if (ExtraVoltage < 5f) ExtraVoltage else ExtraVoltage / 1000.0f
                val ExtraTemperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10f
                val temperature =
                    if (ExtraTemperature > 50f || ExtraTemperature < 0f) 45f else ExtraTemperature
                val extraTechnology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)

                val technology = if (extraTechnology.isNullOrEmpty()) "Li-ion" else extraTechnology
                Log.d("666", "onReceive: $temperature")
                val btrHealth = when (intent.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY, -1)) {
                    BatteryManager.BATTERY_HEALTH_GOOD -> context?.getString(R.string.Good)
                    BatteryManager.BATTERY_HEALTH_OVERHEAT -> context?.getString(R.string.TooHot)
                    else -> context?.getString(R.string.normal)
                }
                val capacity = getBatteryCapacity(context).toInt().toString() + "mAh"
                Log.d("hello", "onReceive: $capacity")

                BatteryInfomation.apply {
                    batteryTemperature.value = temperature
                    batteryPercentage.value = percentage
                    batteryVoltage.value = voltage
                    batteryType.value = technology
                        batteryCapacity.value = capacity
                    if (btrHealth != null) {
                        batteryHealth.value = btrHealth
                    }
                }
            }
            Intent.ACTION_POWER_CONNECTED -> {
                if (!BasePrefers.getPrefsInstance().receiver) return
                getCurrentTime()
                val ChargeStatus = "Your phone is charging"
                BatteryInfomation.apply {
                    chargeStatus1.value = ChargeStatus
                }
                val newIntent = Intent(context, ChargingActivity::class.java)
                newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context?.startActivity(newIntent)
                Toast.makeText(context,"success",Toast.LENGTH_SHORT).show()

            }

            Intent.ACTION_POWER_DISCONNECTED -> {
                val ChargeStatus = "Your phone not charge"
                BatteryInfomation.apply {
                    chargeStatus1.value = ChargeStatus
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime() {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = currentTime.format(formatter)
        BatteryInfomation.timeCharge.value = formattedTime
    }
    @SuppressLint("PrivateApi")
    fun getBatteryCapacity(context: Context?): Double {
        val mPowerProfile: Any
        var batteryCapacity = 0.0
        val powerProfileClass = "com.android.internal.os.PowerProfile"
        val powerProfileMethod = "getBatteryCapacity"
        try {
            mPowerProfile = Class
                .forName(powerProfileClass)
                .getConstructor(Context::class.java)
                .newInstance(context)
            batteryCapacity = Class
                .forName(powerProfileClass)
                .getMethod(powerProfileMethod)
                .invoke(mPowerProfile) as Double
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return batteryCapacity
    }
}