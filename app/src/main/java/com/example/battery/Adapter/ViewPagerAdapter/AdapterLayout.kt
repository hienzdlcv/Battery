package com.example.battery.Adapter.ViewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.battery.UI.Setting.farmentforViewPager.FragmentFont1
import com.example.battery.UI.Setting.farmentforViewPager.FragmentFont2
import com.example.battery.UI.Setting.farmentforViewPager.FragmentLayout1
import com.example.battery.UI.Setting.farmentforViewPager.FragmentLayout2

class AdapterLayout(fragmentActivity: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentActivity,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentLayout1()
            }
            else -> {
                FragmentLayout2()
            }
        }
    }
}