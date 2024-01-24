package com.example.battery.UI.Setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.UI.Setting.farmentforViewPager.Fragment1
import com.example.battery.UI.Setting.farmentforViewPager.Fragment2
import com.example.battery.UI.Setting.farmentforViewPager.FragmentFont1
import com.example.battery.UI.Setting.farmentforViewPager.FragmentLayout1
import com.example.battery.databinding.FragmentSettingBinding
import com.example.battery.databinding.FragmentSettingsBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_setting

    override fun setupUI() {
        super.setupUI()
        naviFragment(FragmentFont1())
        binding.apply {
            dialog1Choose.visibility = View.VISIBLE
            dialog1.visibility = View.GONE
        }
    }

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            dialog1.setOnClickListener {
                naviFragment(FragmentFont1())
                dialog1.visibility = View.GONE
                dialog1Choose.visibility = View.VISIBLE
                dialog3.visibility = View.VISIBLE
                dialog3Choose.visibility = View.GONE
                dialog2.visibility = View.VISIBLE
                dialog2Choose.visibility = View.GONE

            }
            dialog2.setOnClickListener {
                naviFragment(Fragment1())
                dialog2.visibility = View.GONE
                dialog2Choose.visibility = View.VISIBLE
                dialog1.visibility = View.VISIBLE
                dialog1Choose.visibility = View.GONE
                dialog3.visibility = View.VISIBLE
                dialog3Choose.visibility = View.GONE
            }

            dialog3.setOnClickListener {
                naviFragment(FragmentLayout1())
                dialog3.visibility = View.GONE
                dialog3Choose.visibility = View.VISIBLE
                dialog2.visibility = View.VISIBLE
                dialog2Choose.visibility = View.GONE
                dialog1.visibility = View.VISIBLE
                dialog1Choose.visibility = View.GONE
            }
        }
    }

    fun naviFragment(love: Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val yourFragment = love
        fragmentTransaction.replace(R.id.fl5, yourFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}