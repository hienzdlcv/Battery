package com.example.battery

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.battery.Base.BaseActivity
import com.example.battery.Receiver.ChargingService
import com.example.battery.UI.BatteryInfo.BatteryFragment
import com.example.battery.UI.Gallery.GalleryFragment
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.UI.HomeFramgnet.HomeFragment
import com.example.battery.UI.Setting.SettingFragment
import com.example.battery.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.flowWithLifecycle
import com.example.battery.UI.Gallery.Crud
import com.example.battery.UI.Setting.farmentforViewPager.LanguageUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale


//@AndroidEntryPoint //Dagger, hilt
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var intentService: Intent? = null
    private val overlayPermissionRequestCode = 123
    private lateinit var viewModel: GalleryViewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        FirebaseApp.initializeApp(this)
        if (intentService == null) {
            intentService = Intent(this, ChargingService::class.java)
        }
        startForegroundService(intentService)

        changeLanguage(Crud.language.value)
        Log.d("11111", "onStart: ${Crud.language.value} ")


    }

    override fun setupUI() {
        super.setupUI()
        requestPermission()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            showOverlayScreen()
            replaceFragment(HomeFragment())
            binding.BottomNavView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> replaceFragment(HomeFragment())
                    R.id.battery -> replaceFragment(BatteryFragment())
                    R.id.gallery -> replaceFragment(GalleryFragment())
                    R.id.settings -> replaceFragment(SettingFragment())
                    else -> {
                    }
                }
                true
            }
            viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentMan = supportFragmentManager
        val fragmentTrans = fragmentMan.beginTransaction()
        fragmentTrans.replace(R.id.fl1, fragment)
        fragmentTrans.commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (shouldPreventBack()) {
        } else {
            super.onBackPressed()
        }
    }

    private fun shouldPreventBack(): Boolean {
        return true
    }

    private fun showOverlayScreen() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
    }

    fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            showPermissionExplanationDialog()
        } else {
            showOverlayScreen()
        }
    }

    private fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(intent, overlayPermissionRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == overlayPermissionRequestCode) {
            if (Settings.canDrawOverlays(this)) {
                showOverlayScreen()
            } else {
                // Permission not granted, handle accordingly (e.g., show a message)
            }
        }
    }

    private fun showPermissionExplanationDialog() {
        AlertDialog.Builder(this).setTitle("Permission Required")
            .setMessage("This app needs the overlay permission to perform this action.")
            .setPositiveButton("OK") { _, _ ->
                requestOverlayPermission()
            }.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }.create().show()
    }

    private fun changeLanguage(languageCode: String) {
        LanguageUtils.updateLanguage(this, languageCode)
    }
}