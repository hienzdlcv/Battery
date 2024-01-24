package com.example.battery.UI.HomeFramgnet.Search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.battery.Adapter.SearchAdapter
import com.example.battery.Adapter.SearchHistoryAdapter
import com.example.battery.Base.BaseActivity
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.UI.Setup.SetUpActivity
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.ViewModel.WallHeavenViewModel
import com.example.battery.databinding.ActivitySearchImageBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

class SearchImageActivity : BaseActivity<ActivitySearchImageBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_search_image

    private lateinit var mAdapter: SearchHistoryAdapter
    var isFl2Visible = false

    override fun setupUI() {
        super.setupUI()

        binding.apply {
            rcvHistory.setHasFixedSize(true)
            mAdapter = SearchHistoryAdapter(this@SearchImageActivity)
            rcvHistory.layoutManager =
                LinearLayoutManager(this@SearchImageActivity, RecyclerView.VERTICAL, false)
            rcvHistory.adapter = mAdapter
        }
        searchStuff()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupListener() {
        super.setupListener()

        binding.apply {
            searchBar.setOnClickListener {
                isFl2Visible = !isFl2Visible
                fl2.visibility = if (isFl2Visible) View.VISIBLE else View.GONE
            }
        }

        binding.apply {
            searchBar.setOnTouchListener { _, event ->
                val DRAWABLE_RIGHT = 2

                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= searchBar.right - searchBar.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        searchBar.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_close,0)
                        gotoNewFragment()
                        BatteryInfomation.textSearch.value = searchBar.text.toString()
                        addToSearchHistory(searchBar.text.toString())
                    }
                }
                false
            }
        }
        mAdapter.setOnItemClickListener(object : SearchHistoryAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                BatteryInfomation.textSearch.value = mAdapter.mlist[position]
                gotoNewFragment()
            }
        })
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun searchStuff() {
        val sharedPreferences =
            this@SearchImageActivity.getSharedPreferences("SearchHistory", Context.MODE_PRIVATE)
        val savedSearchHistory =
            sharedPreferences.getStringSet("searchHistory", HashSet())?.toList() ?: emptyList()
        val queryList = mutableListOf<String>()
        queryList.addAll(savedSearchHistory)
        binding.deleteMe.setOnClickListener {
            queryList.clear()
            mAdapter.submitList2(queryList)
            mAdapter.notifyDataSetChanged()
        }
        mAdapter.submitList2(queryList)
    }

    private fun addToSearchHistory(query: String) {
        val sharedPreferences =
            this@SearchImageActivity.getSharedPreferences("SearchHistory", Context.MODE_PRIVATE)
        val existingSearchHistory =
            sharedPreferences.getStringSet("searchHistory", HashSet())?.toMutableSet()
                ?: mutableSetOf()
        existingSearchHistory.add(query)
        sharedPreferences.edit().putStringSet("searchHistory", existingSearchHistory).apply()
    }

    fun gotoNewFragment() {
        binding.fl2.visibility = View.VISIBLE
        val fragmentMan = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentMan.beginTransaction()
        val myfragment = SearchFragment()
        fragmentTransaction.replace(R.id.fl2, myfragment)
        fragmentTransaction.commit()
    }
}