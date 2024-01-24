package com.example.battery.UI.BatteryInfo

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.UI.HelpFragmentt
import com.example.battery.databinding.FragmentBatteryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BatteryFragment : BaseFragment<FragmentBatteryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_battery


    @SuppressLint("SetTextI18n")
    override fun setupUI() {
        super.setupUI()

        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch {
                BatteryInfomation.batteryPercentage.flowWithLifecycle(
                    viewLifecycleOwner.lifecycle
                ).collect {
                    txtPctPin.text = "$it%"
                    txtPin.text = when{
                        it == 100 -> "Your battery is full"
                        it in 50..99 -> "Your battery is good"
                        it in 20..49 -> "You need to charge"
                        else -> {"Your battery is extra low"}
                    }
                    textView3.text = "$it%"
                    currentPinBar.setProgressPercentage(it.toDouble(), shouldAnimate = true)
                }
            }
            lifecycleScope.launch {
                BatteryInfomation.timeCharge.flowWithLifecycle(lifecycle).collect{
                    timeCharge.text = it
                }
            }
            Voltage1.text = BatteryInfomation.batteryVoltage.value.toString()
            VoltageBar.setProgressPercentage(BatteryInfomation.batteryVoltage.value.toDouble(), shouldAnimate = true)
            VoltageBar.setAnimationLength(3000)
            temperature1.text = BatteryInfomation.batteryTemperature.value.toString()
            temperatureBar.setProgressPercentage(BatteryInfomation.batteryTemperature.value.toDouble(),shouldAnimate = true)
            batteryHealth.text = BatteryInfomation.batteryHealth.value
            batteryType.text = BatteryInfomation.batteryType.value
            btrCapacity.text = BatteryInfomation.batteryCapacity.value
            Log.d("battery", "setupUI:${btrCapacity.text} ")

        }
    }

    override fun setupListener() {
        super.setupListener()
        binding.apply {
            helpBatteryInfo.setOnClickListener {
                val intent = Intent(requireContext(),HelpFragmentt::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}