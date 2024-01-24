package com.example.battery.UI.Gallery

import android.content.Intent
import android.view.View
import com.example.battery.Adapter.ViewPagerAdapter.AdapterGallery
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.UI.HelpFragmentt
import com.example.battery.databinding.FragmentGalleryBinding
import com.google.android.material.tabs.TabLayoutMediator

class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_gallery

    override fun setupUI() {
        super.setupUI()

        val adapter = AdapterGallery(childFragmentManager, lifecycle)
        binding.viewPagerGallery.adapter = adapter
        TabLayoutMediator(binding.tabLayout1, binding.viewPagerGallery) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Back Ground"
                }

                else -> {
                    tab.text = "Sticker"
                }
            }
        }.attach()
    }

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            txtEdit.setOnClickListener {
                Crud.edit.value = true
                txtEdit.visibility = View.GONE
                UncheckDelete.visibility = View.VISIBLE
                help.visibility = View.GONE
                close.visibility = View.VISIBLE
            }
            close.setOnClickListener {
                binding.apply {
                    Crud.edit.value = false
                    txtEdit.visibility = View.VISIBLE
                    help.visibility = View.VISIBLE
                    close.visibility = View.GONE
                    CheckDelete.visibility = View.GONE
                    UncheckDelete.visibility = View.GONE
                }
            }
            UncheckDelete.setOnClickListener {
                binding.apply {
                    UncheckDelete.visibility = View.GONE
                    CheckDelete.visibility = View.VISIBLE
                    Crud.chooseAll.value = true
                }
            }
            CheckDelete.setOnClickListener {
                binding.apply {
                    UncheckDelete.visibility = View.VISIBLE
                    CheckDelete.visibility = View.GONE
                    Crud.chooseAll.value = false
                }
            }
            help.setOnClickListener {
                val intent =  Intent(requireContext(), HelpFragmentt::class.java)
                startActivity(intent)
            }
        }
    }
}