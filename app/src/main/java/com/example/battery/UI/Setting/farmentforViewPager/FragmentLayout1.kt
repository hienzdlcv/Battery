package com.example.battery.UI.Setting.farmentforViewPager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.AnalogClock
import android.widget.ImageView
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.UI.Gallery.Crud
import com.example.battery.databinding.FragmentLayout1Binding

class FragmentLayout1 : BaseFragment<FragmentLayout1Binding>() {
    override val layoutId: Int
        get() = R.layout.fragment_layout1

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            layout01.setOnClickListener {
                checkbox1.visibility = View.VISIBLE
                checkbox5.visibility = View.GONE
                checkbox2.visibility = View.GONE
                checkbox3.visibility = View.GONE
                checkbox6.visibility = View.GONE
                checkbox4.visibility = View.GONE
                BatteryInfomation.mLayout.value = 1
            }
            layout02.setOnClickListener {
                BatteryInfomation.mLayout.value = 2
                checkbox2.visibility = View.VISIBLE
                checkbox1.visibility = View.GONE
                checkbox5.visibility = View.GONE
                checkbox3.visibility = View.GONE
                checkbox6.visibility = View.GONE
                checkbox4.visibility = View.GONE
            }
            layout03.setOnClickListener {
                checkbox3.visibility = View.VISIBLE
                checkbox1.visibility = View.GONE
                checkbox5.visibility = View.GONE
                checkbox2.visibility = View.GONE
                checkbox6.visibility = View.GONE
                checkbox4.visibility = View.GONE
                BatteryInfomation.mLayout.value = 3
            }
            layout04.setOnClickListener {
                checkbox4.visibility = View.VISIBLE
                checkbox1.visibility = View.GONE
                checkbox5.visibility = View.GONE
                checkbox3.visibility = View.GONE
                checkbox6.visibility = View.GONE
                checkbox2.visibility = View.GONE
                BatteryInfomation.mLayout.value = 4
            }
            layout05.setOnClickListener {
                checkbox5.visibility = View.VISIBLE
                checkbox1.visibility = View.GONE
                checkbox3.visibility = View.GONE
                checkbox3.visibility = View.GONE
                checkbox6.visibility = View.GONE
                checkbox4.visibility = View.GONE
                BatteryInfomation.mLayout.value = 5

            }
            layout06.setOnClickListener {
                checkbox6.visibility = View.VISIBLE
                checkbox1.visibility = View.GONE
                checkbox5.visibility = View.GONE
                checkbox3.visibility = View.GONE
                checkbox2.visibility = View.GONE
                checkbox4.visibility = View.GONE
                BatteryInfomation.mLayout.value = 6
            }
            switchtime.isChecked = BatteryInfomation.timeShow.value
            hide2screen.isChecked = Crud.hide2screen.value
            switchtime.setOnCheckedChangeListener { buttonView, isChecked ->
                BatteryInfomation.timeShow.value = isChecked
            }
            hide2screen.setOnCheckedChangeListener { buttonView, isChecked ->
                Crud.hide2screen.value = isChecked
            }
        }
    }
}