package com.example.battery.UI.Setup

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.battery.Base.BaseActivity
import com.example.battery.DataBase.MyEntity
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.ViewModel.WallHeavenViewModel
import com.example.battery.databinding.ActivitySetUpBinding
import kotlinx.coroutines.launch

class SetUpActivity() : BaseActivity<ActivitySetUpBinding>() {

    private var bundle: Bundle? = null
    private lateinit var myEntity: MyEntity
    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var timeViewModel: WallHeavenViewModel
    override val layoutId: Int
        get() = R.layout.activity_set_up


    @RequiresApi(Build.VERSION_CODES.O)
    override fun setupUI() {
        super.setupUI()
        timeViewModel = ViewModelProvider(this).get(WallHeavenViewModel::class.java)
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        bundle = intent.extras
        setupFromHome()
        setupfromGallery()
        setupFromViewALl()
        setupFromHeaven()
        setupFromSearchingHeaven()
        setUpFromFireBaseViewAll()
        setUpSticker()
        setupStickerFromGallery()
    }

    override fun setupListener() {
        super.setupListener()
        binding.apply {
            eyes.setOnClickListener {
                setupBtn.visibility = View.GONE
                eyes.visibility = View.GONE
                icMenuSetUp.visibility = View.GONE
                eyes1.visibility = View.GONE
            }
            imgPreView.setOnClickListener {
                eyes.visibility = View.VISIBLE
                setupBtn.visibility = View.VISIBLE
                icMenuSetUp.visibility = View.VISIBLE
                eyes1.visibility = View.VISIBLE
            }
            eyes1.setOnClickListener {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val yourFragment = SettingFragment()
                fragmentTransaction.replace(R.id.fl4, yourFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                fl4.visibility = View.VISIBLE
                cardfl4.visibility = View.VISIBLE
            }
            icMenuSetUp.setOnClickListener {
                onBackPressed()
            }

        }
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupfromGallery() {
        val entity = bundle?.getString("entity")

        lifecycleScope.launch {
            BatteryInfomation.timeFont.flowWithLifecycle(lifecycle).collect { string1 ->
                val fontResId = resources.getIdentifier(string1, "font", packageName)
                val love2 = ResourcesCompat.getFont(this@SetUpActivity, fontResId)
                binding.txtTime1.typeface = love2
                binding.txtDate1.typeface = love2
            }
        }

        lifecycleScope.launch {
            BatteryInfomation.timeShow.flowWithLifecycle(lifecycle).collect {
                if (it) {
                    binding.txtTime1.visibility = View.VISIBLE
                    binding.txtDate1.visibility = View.VISIBLE
                } else {
                    binding.txtTime1.visibility = View.GONE
                    binding.txtDate1.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launch {
            BatteryInfomation.mLayout.flowWithLifecycle(lifecycle).collect {
                doFromGallery(it)
            }
        }

        lifecycleScope.launch {
            BatteryInfomation.timeColor.flowWithLifecycle(lifecycle).collect { love ->
                binding.apply {
                    txtTime1.setTextColor(Color.parseColor(love))
                    txtDate1.setTextColor(Color.parseColor(love))
                }
            }
        }

        binding.apply {
            txtDate1.visibility = View.VISIBLE
            txtTime1.visibility = View.VISIBLE
        }
        if (entity != null) {
            Glide.with(this).load(entity).into(binding.imgPreView)
            binding.setupBtn.setOnClickListener {
                galleryViewModel.choosenSticker.observe(this@SetUpActivity, Observer {
                    Log.d("whatthis", "setupfromGallery: $it")
                    if(it!= null) {
                        Glide.with(this).load(it).into(binding.secondPic)
                    }
                })
                galleryViewModel.updateIsChoosen(entity)
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }

    private fun setupStickerFromGallery() {
        val stickerGlr = bundle?.getString("stickerGlr")
        if(stickerGlr != null) {
            Glide.with(this).load(stickerGlr).into(binding.secondPic)
            galleryViewModel.chosenPath.observe(this@SetUpActivity, Observer {
                Log.d("12345", "setupStickerFromGallery:$it ")
                if(it != null) {
                    Glide.with(this).load(it).into(binding.imgPreView)
                }
            })
            binding.setupBtn.setOnClickListener {
                galleryViewModel.updateIsChoosenPath(stickerGlr)
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }

    private fun setupFromHome() {
        val api = bundle?.getString("api")
        if (api != null) {
            Glide.with(this).load(api).into(binding.imgPreView)
            binding.setupBtn.setOnClickListener {
                val save = MyEntity(0, api, false)
                galleryViewModel.addItem(save)
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }


    private fun setupFromViewALl() {
        val viewAll = bundle?.getString("viewAll")
        if (viewAll != null) {
            Glide.with(this).load(viewAll).into(binding.imgPreView)
            binding.setupBtn.setOnClickListener {
                val save = MyEntity(0, viewAll, false)
                galleryViewModel.addItem(save)
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }

    private fun setupFromHeaven() {
        val heaven = bundle?.getString("api3")
        if (heaven != null) {
            Glide.with(this).load(heaven).into(binding.imgPreView)
            binding.setupBtn.setOnClickListener {
                val save = MyEntity(0, heaven, false)
                galleryViewModel.addItem(save)
                onBackPressed()
            }
        }
    }

    private fun setUpSticker() {
        val love = bundle?.getString("sticker")
        Log.d("whatthis", "setUpSticker: $love")
        if(love != null) {
            Glide.with(this).load(love).into(binding.secondPic)
            binding.setupBtn.setOnClickListener {
                val save = MyEntity(0,null,false,love)
                galleryViewModel.addItem(save)
                onBackPressed()
            }
        }
    }

    private fun setupFromSearchingHeaven() {
        val searchWH = bundle?.getString("searchWH")
        val searchWH2 = bundle?.getString("searchWH2")

        if (searchWH2 != null) {
            val love =
                "https://firebasestorage.googleapis.com/v0/b/batterycharge-3632b.appspot.com/o/images%2F${searchWH2}?alt=media"
            Glide.with(this).load(love).into(binding.imgPreView)
            binding.setupBtn.setOnClickListener {
                val save = MyEntity(0, love, false)
                galleryViewModel.addItem(save)
                onBackPressed()
            }
        }

        if (searchWH != null) {
            Glide.with(this).load(searchWH).into(binding.imgPreView)
            binding.setupBtn.setOnClickListener {
                val save = MyEntity(0, searchWH, false)
                galleryViewModel.addItem(save)
                onBackPressed()
            }
        }
    }

    private fun setUpFromFireBaseViewAll() {
        val fireb = bundle?.getString("viewAll")
        val fireb2 = bundle?.getString("viewAll2")
        if (fireb != null) {
            val love =
                "https://firebasestorage.googleapis.com/v0/b/batterycharge-3632b.appspot.com/o/images%2F$fireb2%2F$fireb?alt=media"
            Glide.with(this).load(love).into(binding.imgPreView)
            binding.setupBtn.setOnClickListener {
                val save = MyEntity(0, love, false)
                galleryViewModel.addItem(save)
                onBackPressed()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun doFromGallery(type: Int) {
        when (type) {
            1 -> {
                binding.txtTime1.visibility = View.VISIBLE
                binding.apply {
                    clock1.visibility = View.GONE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.GONE
                    secondPic.visibility = View.GONE
                    txtDate1.visibility = View.VISIBLE
                }
                timeViewModel.updateTimeNow()
                binding.txtTime1.text = timeViewModel.timeNow.value
            }

            2 -> {
                binding.apply {
                    txtDate1.visibility = View.VISIBLE
                    txtTime1.visibility = View.VISIBLE
                    clock1.visibility = View.GONE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.GONE
                    secondPic.visibility = View.VISIBLE
                }
                timeViewModel.updateTimeNow1()
                binding.txtTime1.text = timeViewModel.timeNow1.value
                Toast.makeText(this@SetUpActivity,"Vcl2",Toast.LENGTH_SHORT).show()

            }

            3 -> {
                binding.apply {
                    txtDate1.visibility = View.VISIBLE
                    clock1.visibility = View.GONE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.GONE
                    secondPic.visibility = View.VISIBLE
                }
                binding.txtTime1.visibility = View.VISIBLE
                timeViewModel.updateTimeNow2()
                binding.txtTime1.text = timeViewModel.timeNow2.value
                Toast.makeText(this@SetUpActivity,"Vcl3",Toast.LENGTH_SHORT).show()
            }

            4 -> {
                binding.apply {
                    txtDate1.visibility = View.VISIBLE
                    secondPic.visibility = View.VISIBLE
                    txtTime1.visibility = View.GONE
                    clock1.visibility = View.VISIBLE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.GONE
                }
            }

            5 -> {
                binding.apply {
                    txtDate1.visibility = View.VISIBLE
                    secondPic.visibility = View.VISIBLE
                    txtTime1.visibility = View.GONE
                    clock2.visibility = View.VISIBLE
                    clock1.visibility = View.GONE
                    clock3.visibility = View.GONE
                }
            }

            6 -> {
                binding.apply {
                    txtDate1.visibility = View.VISIBLE
                    secondPic.visibility = View.VISIBLE
                    txtTime1.visibility = View.GONE
                    clock1.visibility = View.GONE
                    clock2.visibility = View.GONE
                    clock3.visibility = View.VISIBLE
                }
            }
        }
    }

}