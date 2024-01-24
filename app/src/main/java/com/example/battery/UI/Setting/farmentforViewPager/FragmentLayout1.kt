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
import com.example.battery.databinding.FragmentLayout1Binding

class FragmentLayout1 : BaseFragment<FragmentLayout1Binding>() {
    override val layoutId: Int
        get() = R.layout.fragment_layout1

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            layout01.setOnClickListener { BatteryInfomation.mLayout.value = 1 }
            layout02.setOnClickListener { BatteryInfomation.mLayout.value = 2 }
            layout03.setOnClickListener { BatteryInfomation.mLayout.value = 3 }
            layout04.setOnClickListener { BatteryInfomation.mLayout.value = 4 }
            layout05.setOnClickListener { BatteryInfomation.mLayout.value = 5 }
            layout06.setOnClickListener { BatteryInfomation.mLayout.value = 6 }
        }
    }
}