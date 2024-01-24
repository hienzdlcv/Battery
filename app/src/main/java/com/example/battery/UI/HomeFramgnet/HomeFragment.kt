package com.example.battery.UI.HomeFramgnet

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.battery.Adapter.HeavenAdapter
import com.example.battery.Adapter.HomeAdapter
import com.example.battery.Api.ApiFromWallHeaven.ImageModel1
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.UI.HelpFragmentt
import com.example.battery.UI.HomeFramgnet.Search.SearchImageActivity
import com.example.battery.UI.Setup.SetUpActivity
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.databinding.FragmentHomeBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var mAdapter: HomeAdapter
    private lateinit var mAdapterSticker: HomeAdapter
    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var mheavenAdapter: HeavenAdapter

    private lateinit var storageReference: StorageReference

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun setupUI() {
        super.setupUI()
        binding.apply {

            galleryViewModel = ViewModelProvider(this@HomeFragment).get(GalleryViewModel::class.java)

            rcvApi1.setHasFixedSize(true)
            mAdapter = HomeAdapter(requireContext())
            rcvApi1.adapter = mAdapter
            rcvApi1.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)

            rcvApi2.setHasFixedSize(true)
            mAdapterSticker = HomeAdapter(requireContext())
            rcvApi2.adapter = mAdapterSticker
            rcvApi2.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

            rcvApi3.setHasFixedSize(true)
            mheavenAdapter = HeavenAdapter(requireContext(), emptyList())
            rcvApi3.adapter = mheavenAdapter
            rcvApi3.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)

            galleryViewModel.fetchTrendingData()
            galleryViewModel.listGifs.observe(viewLifecycleOwner, Observer {
                mAdapter.submitList(it)
            })

        }
        setupProgressBar()
        setupAnimeGif()

        storageReference = FirebaseStorage.getInstance().getReference("images/")
        storageReference.listAll()
            .addOnSuccessListener { listResult ->
                val itemUrls = mutableListOf<String>()
                mheavenAdapter.updateData(itemUrls)
                for (item in listResult.items) {
                    val itemName = item.name
                    val itemUrl = item.downloadUrl.toString()
                    Log.d("haha", "setupUI: $itemUrl haha $itemName ")
                    itemUrls.add(itemName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("pain", "Error fetching data: ${exception.message}")
            }
    }

    @SuppressLint("SetTextI18n")
    fun setupProgressBar() {
        lifecycleScope.launch {
            BatteryInfomation.chargeStatus1.flowWithLifecycle(lifecycle).collect{
                BatteryInfomation.chargeStatus1.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                    binding.chargStatus.text = it
                }
            }
        }
        lifecycleScope.launch {
            BatteryInfomation.batteryPercentage.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                binding.apply {
                    textView4.text = "$it%"
                    pinC.text = "$it%"
                    progressBar1.apply {
                        setProgressWithAnimation(it.toFloat(), 3000)
                        progressMax = 100f
                        backgroundProgressBarWidth = 10f
                        progressBarWidth = 10f
                        progressBarColor = Color.GREEN
                        progressDirection = CircularProgressBar.ProgressDirection.TO_LEFT
                    }
                }
            }
        }
    }

    fun setupAnimeGif() {
            galleryViewModel.fetchSticker()
            galleryViewModel.listRandomGif.observe(viewLifecycleOwner, Observer {
                mAdapterSticker.submitList2(it)
                Log.d("6666", "setUpRandomGif: ${it}")
            })
    }

    override fun setupListener() {
        super.setupListener()
        mAdapterSticker.setOnItemClickListener(object : HomeAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val love = mAdapterSticker.mlist2[position].image.dataOri.url
                Log.d("whatthis", "onItemClick: $love")
                val intent = Intent(requireContext(),SetUpActivity::class.java)
                intent.putExtra("sticker",love)
                startActivity(intent)
            }
        })

        mAdapter.setOnItemClickListener(object : HomeAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val love= mAdapter.mList[position].images.ogImage.url
                val intent = Intent(requireContext(),SetUpActivity::class.java)
                intent.putExtra("api",love)
                startActivity(intent)
            }
        })
        mheavenAdapter.setOnItemClickListener(object : HeavenAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val love = mheavenAdapter.imageList[position]
                val intent = Intent(requireContext(),SetUpActivity::class.java)
                intent.putExtra("searchWH2",love)
                startActivity(intent)
            }

        })
        binding.apply {
            viewAll.setOnClickListener {
                val intent = Intent(requireContext(),ViewAllActivity::class.java)
                startActivity(intent)
            }
            gotosearch.setOnClickListener {
                val intent = Intent(requireContext(),ViewAllActivity::class.java)
                intent.putExtra("search1","1")
                startActivity(intent)
            }
            helphome.setOnClickListener {
                val intent = Intent(requireContext(),HelpFragmentt::class.java)
                startActivity(intent)
            }
            searchMore1.setOnClickListener {
                val intent = Intent(requireContext(), SearchImageActivity::class.java)
                startActivity(intent)
            }
        }
    }

}