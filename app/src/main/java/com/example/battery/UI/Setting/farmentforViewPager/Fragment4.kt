package com.example.battery.UI.Setting.farmentforViewPager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.databinding.Fragment4Binding

class Fragment4 : BaseFragment<Fragment4Binding>() {
    override val layoutId: Int
        get() = R.layout.fragment_4

    override fun setupListener() {
        super.setupListener()
        binding.apply {
            color13.setOnClickListener { BatteryInfomation.timeColor.value = "#184D47"}
            color14.setOnClickListener { BatteryInfomation.timeColor.value = "#96BB7C"}
            color15.setOnClickListener { BatteryInfomation.timeColor.value = "#D6EFC7"}
            color16.setOnClickListener { BatteryInfomation.timeColor.value = "#FAD586"}

        }
    }
}