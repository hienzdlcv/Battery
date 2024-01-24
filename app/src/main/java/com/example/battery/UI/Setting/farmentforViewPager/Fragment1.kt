package com.example.battery.UI.Setting.farmentforViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.databinding.Fragment1Binding
import com.example.battery.databinding.Fragment2Binding

class Fragment1 : BaseFragment<Fragment1Binding>() {
    override val layoutId: Int
        get() = R.layout.fragment_1

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            color1.setOnClickListener {BatteryInfomation.timeColor.value = "#89CCF7"}
            color2.setOnClickListener {BatteryInfomation.timeColor.value = "#282A39"}
            color3.setOnClickListener {BatteryInfomation.timeColor.value = "#FFC90B"}
            color4.setOnClickListener {BatteryInfomation.timeColor.value = "#F4F5F9"}
            color5.setOnClickListener { BatteryInfomation.timeColor.value = "#B39DFF"}
            color6.setOnClickListener { BatteryInfomation.timeColor.value = "#FFA5ED"}
            color7.setOnClickListener { BatteryInfomation.timeColor.value = "#31FBEF"}
            color8.setOnClickListener { BatteryInfomation.timeColor.value = "#FF9533"}
            color9.setOnClickListener { BatteryInfomation.timeColor.value = "#F5EFE7"}
            color10.setOnClickListener { BatteryInfomation.timeColor.value = "#D8C4B6"}
            color11.setOnClickListener { BatteryInfomation.timeColor.value = "#4F709C"}
            color12.setOnClickListener { BatteryInfomation.timeColor.value = "#191D88"}
            color13.setOnClickListener { BatteryInfomation.timeColor.value = "#184D47"}
            color14.setOnClickListener { BatteryInfomation.timeColor.value = "#96BB7C"}
            color15.setOnClickListener { BatteryInfomation.timeColor.value = "#D6EFC7"}
            color16.setOnClickListener { BatteryInfomation.timeColor.value = "#FAD586"}
        }
    }
}