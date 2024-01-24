package com.example.battery.UI.Setting.farmentforViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.databinding.FragmentLayout2Binding

class FragmentLayout2 : BaseFragment<FragmentLayout2Binding>() {
    override val layoutId: Int
        get() = R.layout.fragment_layout2

    override fun setupListener() {
        super.setupListener()
        binding.apply {
            layout04.setOnClickListener { BatteryInfomation.mLayout.value = 4 }
            layout05.setOnClickListener { BatteryInfomation.mLayout.value = 5 }
            layout06.setOnClickListener { BatteryInfomation.mLayout.value = 6 }
        }
    }
}