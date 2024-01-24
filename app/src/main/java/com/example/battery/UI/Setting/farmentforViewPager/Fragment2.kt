package com.example.battery.UI.Setting.farmentforViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.databinding.Fragment2Binding

class Fragment2 : BaseFragment<Fragment2Binding>() {
    override val layoutId: Int
        get() = R.layout.fragment_2

    override fun setupListener() {
        super.setupListener()
        binding.apply {
            color5.setOnClickListener { BatteryInfomation.timeColor.value = "#B39DFF"}
            color6.setOnClickListener { BatteryInfomation.timeColor.value = "#FFA5ED"}
            color7.setOnClickListener { BatteryInfomation.timeColor.value = "#31FBEF"}
            color8.setOnClickListener { BatteryInfomation.timeColor.value = "#FF9533"}
        }
    }

}