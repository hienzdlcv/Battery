package com.example.battery.UI.Setting.farmentforViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.databinding.Fragment3Binding

class Fragment3 : BaseFragment<Fragment3Binding>() {
    override val layoutId: Int
        get() = R.layout.fragment_3

    override fun setupListener() {
        super.setupListener()
        binding.apply {
            color9.setOnClickListener { BatteryInfomation.timeColor.value = "#F5EFE7"}
            color10.setOnClickListener { BatteryInfomation.timeColor.value = "#D8C4B6"}
            color11.setOnClickListener { BatteryInfomation.timeColor.value = "#4F709C"}
            color12.setOnClickListener { BatteryInfomation.timeColor.value = "#191D88"}
        }
    }
}