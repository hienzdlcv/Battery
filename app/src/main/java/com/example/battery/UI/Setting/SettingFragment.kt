package com.example.battery.UI.Setting

import android.app.AlertDialog
import android.content.Intent
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.databinding.FragmentSettingsBinding


class SettingFragment : BaseFragment<FragmentSettingsBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_settings

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            language.setOnClickListener{
                val intent = Intent(requireContext(), LanguageActivity::class.java)
                startActivity(intent)
            }
            helpSetting.setOnClickListener {
                val intent = Intent(requireContext(),SettingFragment::class.java)
                startActivity(intent)
            }
            binding.settingRateus.setOnClickListener {
                val rateUsDialog = RateUsDialog(requireContext())
                rateUsDialog.show()
            }

        }
    }

}