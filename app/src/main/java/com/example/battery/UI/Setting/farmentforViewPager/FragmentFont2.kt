package com.example.battery.UI.Setting.farmentforViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.databinding.FragmentFont2Binding


class FragmentFont2 : BaseFragment<FragmentFont2Binding>() {
    override val layoutId: Int
        get() = R.layout.fragment_font2

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            cabin.setOnClickListener { BatteryInfomation.timeFont.value = "cabin" }
            firesans.setOnClickListener { BatteryInfomation.timeFont.value = "firesans" }
            worksan.setOnClickListener { BatteryInfomation.timeFont.value = "worksan" }
            nunito.setOnClickListener { BatteryInfomation.timeFont.value = "nunito" }
        }
    }
}