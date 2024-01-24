package com.example.battery.UI.HomeFramgnet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.battery.Adapter.CategoryAdapter
import com.example.battery.Adapter.ViewAllAdapter
import com.example.battery.Adapter.ViewAllAdapter2
import com.example.battery.Api.ApiFromWallHeaven.DataCate
import com.example.battery.Base.BaseActivity
import com.example.battery.CategoryAdapter2
import com.example.battery.R
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.UI.Setup.SetUpActivity
import com.example.battery.databinding.ActivityViewAllBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.checkerframework.common.returnsreceiver.qual.This

class ViewAllActivity : BaseActivity<ActivityViewAllBinding>() {

    private lateinit var mAdapter: CategoryAdapter
    private lateinit var mAdapterSubCate: CategoryAdapter
    private lateinit var mAdapterViewAll: ViewAllAdapter
    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var gifs1: String
    private lateinit var gifs2: String

    private lateinit var list: ArrayList<DataCate>

    private lateinit var mAdapter2: CategoryAdapter2
    private lateinit var mAdapterViewAll2: ViewAllAdapter2

    private lateinit var storageReference: StorageReference

    private var bundle: Bundle? = null

    override val layoutId: Int
        get() = R.layout.activity_view_all

    override fun setupUI() {
        super.setupUI()
        bundle = intent.extras

        binding.apply {

            galleryViewModel =
                ViewModelProvider(this@ViewAllActivity).get(GalleryViewModel::class.java)

            rcvCate.setHasFixedSize(true)
            mAdapter = CategoryAdapter(this@ViewAllActivity)
            rcvCate.adapter = mAdapter
            rcvCate.layoutManager =
                LinearLayoutManager(this@ViewAllActivity, RecyclerView.HORIZONTAL, false)

            rcvSubCate.setHasFixedSize(true)
            mAdapterSubCate = CategoryAdapter(this@ViewAllActivity)
            rcvSubCate.adapter = mAdapterSubCate
            rcvSubCate.layoutManager =
                LinearLayoutManager(this@ViewAllActivity, RecyclerView.HORIZONTAL, false)

            rcvViewALl.setHasFixedSize(true)
            mAdapterViewAll = ViewAllAdapter(this@ViewAllActivity)
            rcvViewALl.adapter = mAdapterViewAll
            rcvViewALl.layoutManager = GridLayoutManager(this@ViewAllActivity, 2)


            galleryViewModel.fetchTrendingData()
            galleryViewModel.listCate.observe(this@ViewAllActivity, Observer {
                mAdapter.submitList(it)
            })
            galleryViewModel.fetchCateGory()

            loadfromFireBase()

            subCategory()
            loadGifForSubcategory()
        }
    }

    private fun loadfromFireBase() {
        val love = bundle?.getString("search1")
        if (love != null) {
            binding.apply {
                list = ArrayList()
                rcvCate.setHasFixedSize(true)
                rcvCate.layoutManager =
                    LinearLayoutManager(this@ViewAllActivity, RecyclerView.HORIZONTAL, false)
                sampleData()
                mAdapter2 = CategoryAdapter2(list)
                rcvCate.adapter = mAdapter2

                rcvViewALl.setHasFixedSize(true)
                rcvViewALl.layoutManager = GridLayoutManager(this@ViewAllActivity, 2)
                mAdapterViewAll2 = ViewAllAdapter2(this@ViewAllActivity)
                rcvViewALl.adapter = mAdapterViewAll2

                mAdapter2.SetOnItemClickListener(object : CategoryAdapter2.onItemClickListener {
                    override fun OnItemClick(position: Int) {
                        gifs1 = mAdapter2.mList[position].text
                        storageReference =
                            FirebaseStorage.getInstance().getReference("images/$gifs1/")
                        storageReference.listAll().addOnSuccessListener {
                            val itemUrls = mutableListOf<String>()
                            mAdapterViewAll2.updateData(itemUrls)
                            for (item in it.items) {
                                val itemName = item.name
                                val itemUrl = item.downloadUrl.toString()
                                Log.d("haha", "setupUI: $itemUrl haha $itemName ")
                                itemUrls.add(itemName)
                            }
                        }.addOnFailureListener { exception ->
                                Log.e("pain", "Error fetching data: ${exception.message}")
                            }
                        mAdapterViewAll2.setupHeheValue(gifs1)
                    }
                })

                mAdapterViewAll2.setOnItemClickListener(object : ViewAllAdapter2.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val love1 = mAdapterViewAll2.mList[position]
                        val love2 = mAdapterViewAll2.hehe
                        val intent = Intent(this@ViewAllActivity,SetUpActivity::class.java)
                        intent.putExtra("viewAll",love1)
                        intent.putExtra("viewAll2",love2)
                        startActivity(intent)
                    }

                })
            }
        }
    }

    private fun sampleData() {
        list.add(DataCate("anime"))
        list.add(DataCate("random"))
        list.add(DataCate("Cool"))
        list.add(DataCate("berserk"))
        list.add(DataCate("natur"))
    }

    private fun subCategory() {
        mAdapter.setOnItemClickListener1(object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                gifs1 = mAdapter.mList[position].nameEncoded
                Log.d("dcm", "onItemClick: $gifs1")
                galleryViewModel.fetchSubCategory(gifs1)
            }
        })
        galleryViewModel.listSubCate.observe(this@ViewAllActivity, Observer {
            mAdapterSubCate.submitList2(it)
        })
    }

    private fun loadGifForSubcategory() {
        mAdapterSubCate.setOnItemClickListener1(object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                gifs2 = mAdapterSubCate.mList2[position].nameEncoded
                galleryViewModel.fetchSubGif(gifs2)
            }
        })
        galleryViewModel.listSubGif.observe(this@ViewAllActivity, Observer {
            mAdapterViewAll.submitList(it)
        })
    }

    override fun setupListener() {
        super.setupListener()
        mAdapterViewAll.setOnClickListener(object : ViewAllAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val love = mAdapterViewAll.mList[position].images.still480w.url
                val intent = Intent(this@ViewAllActivity, SetUpActivity::class.java)
                intent.putExtra("viewAll", love)
                startActivity(intent)
            }
        })
    }
}