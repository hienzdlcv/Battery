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
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts


//@AndroidEntryPoint //Dagger, hilt
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var intentService: Intent? = null
    private val overlayPermissionRequestCode = 123
    private lateinit var viewModel: GalleryViewModel

    private lateinit var permissionLaucher: ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted = false
    private var isOverLayPermissionGranted = false
    private var isILoveYouPermissionGranted = false

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
    }

    override fun setupUI() {
        super.setupUI()
        requestPermission()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        replaceFragment(HomeFragment())

        binding.BottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
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
        //setLockScreenWallpaper(this,love.toString())

        permissionLaucher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            isReadPermissionGranted = it[Manifest.permission.READ_EXTERNAL_STORAGE] ?:isReadPermissionGranted
            isILoveYouPermissionGranted = it[Manifest.permission.BATTERY_STATS] ?: isILoveYouPermissionGranted
            isOverLayPermissionGranted = it[Manifest.permission.SYSTEM_ALERT_WINDOW] ?: isOverLayPermissionGranted
        }

        test01()
    }

    private fun test01() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        isILoveYouPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.BATTERY_STATS) == PackageManager.PERMISSION_GRANTED

        isOverLayPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.SYSTEM_ALERT_WINDOW) == PackageManager.PERMISSION_GRANTED

        val permissionRequest : MutableList<String> = ArrayList()

        if(!isReadPermissionGranted) {
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if(!isILoveYouPermissionGranted) {
            permissionRequest.add(Manifest.permission.BATTERY_STATS)
        }

        if(!isOverLayPermissionGranted) {
            permissionRequest.add(Manifest.permission.SYSTEM_ALERT_WINDOW)
        }

        if(permissionRequest.isNotEmpty()){
            permissionLaucher.launch(permissionRequest.toTypedArray())
        }
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

    fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            // If not granted, request the permission
            requestOverlayPermission()
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

    private fun showOverlayScreen() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
    }
}