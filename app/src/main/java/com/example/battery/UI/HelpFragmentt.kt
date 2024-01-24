package com.example.battery.UI

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.example.battery.Base.BaseActivity
import com.example.battery.R
import com.example.battery.databinding.ActivityHelpFragmenttBinding

class HelpFragmentt : BaseActivity<ActivityHelpFragmenttBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_help_fragmentt

    override fun setupListener() {
        binding.apply {
            icMenu.setOnClickListener { onBackPressed() }
            itemHelpSwitch.setOnClickListener {
                if (itemHelpSwitch.isChecked) {
                    binding.itemHelpSwitch.isChecked = false
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + this@HelpFragmentt.packageName)
                    )
                    requestOverlayPermissionLauncher.launch(intent)
                }
            }
            itemHelpSwitch2.setOnClickListener {
                if (itemHelpSwitch2.isChecked) {
                    binding.itemHelpSwitch2.isChecked = false
                    val intent = Intent(
                        Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                        Uri.parse("package:${this@HelpFragmentt.packageName}")
                    )
                    requestOptimizationPermissionLauncher.launch(intent)
                }
            }
        }
    }

    private val requestOverlayPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) binding.itemHelpSwitch.isChecked = true
    }

    private val requestOptimizationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) binding.itemHelpSwitch2.isChecked = true
    }

}