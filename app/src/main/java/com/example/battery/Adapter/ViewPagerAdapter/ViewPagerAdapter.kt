package com.example.battery.Adapter.ViewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.battery.UI.Setting.farmentforViewPager.Fragment1
import com.example.battery.UI.Setting.farmentforViewPager.Fragment2
import com.example.battery.UI.Setting.farmentforViewPager.Fragment3
import com.example.battery.UI.Setting.farmentforViewPager.Fragment4

class ViewPagerAdapter(fragmentActivity: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fragmentActivity,lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {Fragment1()}
            1 -> {Fragment2()}
            2 -> {Fragment3()}
            else -> {Fragment4()}
        }
    }
}