package com.example.battery.Receiver

import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.battery.Base.BaseActivity
import com.example.battery.Base.BasePrefers
import com.example.battery.Constants.Constants
import com.example.battery.R
import com.example.battery.UI.Setup.SetupSetting
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.ViewModel.WallHeavenViewModel
import com.example.battery.databinding.ActivityChargingBinding
import com.google.gson.Gson
import com.pedromassango.doubleclick.DoubleClick
import com.pedromassango.doubleclick.DoubleClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChargingActivity : BaseActivity<ActivityChargingBinding>() {

    private lateinit var viewModel: GalleryViewModel
    private lateinit var timeViewModel: WallHeavenViewModel

    override val layoutId: Int
        get() = R.layout.activity_charging

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun setupUI() {
        super.setupUI()

        setShowWhenLocked(true)
        setTurnScreenOn(true)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )

        viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        timeViewModel = ViewModelProvider(this).get(WallHeavenViewModel::class.java)
        viewModel.theChoosenOne()
        viewModel.theChoosenSticker()
        viewModel.choosenSticker.observe(this@ChargingActivity, Observer {
            if(it != null) {
                val love = it
                Glide.with(this@ChargingActivity).load(love).into(binding.secondPic2)
            }
        })
        viewModel.chosenPath.observe(this@ChargingActivity, Observer {
            if (it != null) {
                val love = it
                Glide.with(this@ChargingActivity).load(love).into(binding.hehe)
            }
        })

        binding.apply {
            animateView.playAnimation()
            Handler().postDelayed({
                animateView.visibility = View.GONE
                hehe.visibility = View.VISIBLE

                val currentDate = LocalDateTime.now()
                val fomater2 = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                binding.txtDate.text = currentDate.format(fomater2)

                lifecycleScope.launch {
                    BatteryInfomation.timeColor.flowWithLifecycle(lifecycle).collect {
                        binding.txtTime.setTextColor(Color.parseColor(it))
                        binding.txtDate.setTextColor(Color.parseColor(it))
                    }
                }

                lifecycleScope.launch {
                    BatteryInfomation.timeFont.flowWithLifecycle(lifecycle).collect { string1 ->
                        val fontResId = resources.getIdentifier(string1, "font", packageName)
                        val love2 = ResourcesCompat.getFont(this@ChargingActivity, fontResId)
                        binding.txtDate.typeface = love2
                        binding.txtTime.typeface = love2
                    }
                }

                lifecycleScope.launch {
                    BatteryInfomation.timeShow.flowWithLifecycle(lifecycle).collect {
                        if (it) {
                            binding.txtTime.visibility = View.VISIBLE
                            binding.txtDate.visibility = View.VISIBLE
                        } else {
                            binding.txtTime.visibility = View.GONE
                            binding.txtDate.visibility = View.GONE
                        }
                    }
                }

                lifecycleScope.launch {
                    BatteryInfomation.mLayout.flowWithLifecycle(lifecycle).collect {
                        updateTimeBasedOnLayout(it)
                    }
                }

            }, 2800)
        }
        setonClose()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun updateTimeBasedOnLayout(layoutType: Int) {
        when (layoutType) {
            1 -> {
                binding.txtTime.visibility = View.VISIBLE
                binding.apply {
                    clock1.visibility = View.GONE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.GONE
                    secondPic2.visibility = View.GONE
                    txtDate.visibility = View.VISIBLE
                }
                timeViewModel.updateTimeNow()
                binding.txtTime.text = timeViewModel.timeNow.value
            }

            2 -> {
                binding.apply {
                    txtTime.visibility = View.VISIBLE
                    txtTime.visibility = View.VISIBLE
                    clock1.visibility = View.GONE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.GONE
                    secondPic2.visibility = View.VISIBLE
                }
                timeViewModel.updateTimeNow1()
                binding.txtTime.text = timeViewModel.timeNow1.value

            }

            3 -> {
                binding.apply {
                    txtDate.visibility = View.VISIBLE
                    clock1.visibility = View.GONE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.GONE
                    secondPic2.visibility = View.VISIBLE
                }
                binding.txtTime.visibility = View.VISIBLE
                timeViewModel.updateTimeNow2()
                binding.txtTime.text = timeViewModel.timeNow2.value
                Toast.makeText(this@ChargingActivity,"Vcl3", Toast.LENGTH_SHORT).show()
            }

            4 -> {
                binding.apply {
                    txtDate.visibility = View.VISIBLE
                    secondPic2.visibility = View.VISIBLE
                    txtTime.visibility = View.GONE
                    clock1.visibility = View.VISIBLE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.GONE
                }
            }

            5 -> {
                binding.apply {
                    txtDate.visibility = View.VISIBLE
                    secondPic2.visibility = View.VISIBLE
                    txtTime.visibility = View.GONE
                    clock2.visibility = View.VISIBLE
                    clock1.visibility = View.GONE
                    clock3.visibility = View.GONE
                }
            }

            6 -> {
                binding.apply {
                    txtDate.visibility = View.VISIBLE
                    secondPic2.visibility = View.VISIBLE
                    txtTime.visibility = View.GONE
                    clock1.visibility = View.GONE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setonClose() {
        val setting = Gson().fromJson(
            BasePrefers.getPrefsInstance().setupSetting, SetupSetting::class.java
        )
        binding.hehe.setOnClickListener(DoubleClick(object : DoubleClickListener {
            override fun onSingleClick(view: View?) {
                if (setting.closingMethod == Constants.SINGLE_TAP) {
                    finishAffinity()
                }
            }

            override fun onDoubleClick(view: View?) {
                if (setting.closingMethod == Constants.DOUBLE_TAP) {
                    finishAffinity()
                }
            }
        }))
    }
}