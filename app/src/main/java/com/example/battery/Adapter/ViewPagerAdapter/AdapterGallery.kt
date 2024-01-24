package com.example.battery.Adapter.ViewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.battery.UI.Gallery.TabLayoutFragment.BackGround
import com.example.battery.UI.Gallery.TabLayoutFragment.StickerFragment
import com.example.battery.databinding.FragmentStickerBinding

class AdapterGallery(fragmentActivity: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentActivity,lifecycle) {
    override fun getItemCount():Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                BackGround()
            }
            else -> {
                StickerFragment()
            }
        }
    }
}