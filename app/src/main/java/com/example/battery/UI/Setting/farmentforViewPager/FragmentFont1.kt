package com.example.battery.UI.Setting.farmentforViewPager

import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.databinding.FragmentFont1Binding

class FragmentFont1 : BaseFragment<FragmentFont1Binding>() {
    override val layoutId: Int
        get() = R.layout.fragment_font1

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            montserrat.setOnClickListener { BatteryInfomation.timeFont.value = "montserrat" }
            notosans.setOnClickListener { BatteryInfomation.timeFont.value = "notosans" }
            opensans.setOnClickListener { BatteryInfomation.timeFont.value = "opensans" }
            roboto.setOnClickListener { BatteryInfomation.timeFont.value = "roboto" }
            cabin.setOnClickListener { BatteryInfomation.timeFont.value = "cabin" }
            firesans.setOnClickListener { BatteryInfomation.timeFont.value = "firesans" }
            worksan.setOnClickListener { BatteryInfomation.timeFont.value = "worksan" }
            nunito.setOnClickListener { BatteryInfomation.timeFont.value = "nunito" }

            barlow.setOnClickListener { BatteryInfomation.timeFont.value = "barlow" }
            chakraPetch.setOnClickListener { BatteryInfomation.timeFont.value = "chakrapetch" }
            josefinsans.setOnClickListener { BatteryInfomation.timeFont.value = "josefinsans" }
            lexend.setOnClickListener { BatteryInfomation.timeFont.value = "lexend" }
            lora.setOnClickListener { BatteryInfomation.timeFont.value = "lora" }
            pacifico.setOnClickListener { BatteryInfomation.timeFont.value = "pacifico" }
            manrope.setOnClickListener { BatteryInfomation.timeFont.value = "manrope" }
            quicksand.setOnClickListener { BatteryInfomation.timeFont.value = "quicksand" }
        }
    }
}