package com.example.battery.UI.HomeFramgnet.Search

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.battery.Adapter.SearchAdapter
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.Receiver.BatteryInfomation
import com.example.battery.UI.Setup.SetUpActivity
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.databinding.FragmentSearchBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private lateinit var mAdapter: SearchAdapter
    private lateinit var viewModel2: GalleryViewModel

    private lateinit var storageReference: StorageReference

    override val layoutId: Int
        get() = R.layout.fragment_search

    override fun setupUI() {
        super.setupUI()
        viewModel2 = ViewModelProvider(this@SearchFragment).get(GalleryViewModel::class.java)

        binding.apply {
            rcvSearch.setHasFixedSize(true)
            mAdapter = SearchAdapter(requireContext())
            rcvSearch.layoutManager = GridLayoutManager(requireContext(), 2)
            rcvSearch.adapter = mAdapter
        }

        lifecycleScope.launch {
            BatteryInfomation.textSearch.flowWithLifecycle(lifecycle).collect{
                setupSearchRandomGif(it)
            }
        }

    }

    private fun setupSearchRandomGif(love: String) {
        if (isAdded) {
            val mFbList = mutableListOf<String>()
            storageReference = FirebaseStorage.getInstance().getReference("images/")
            storageReference.listAll().addOnSuccessListener {
                for (item in it.items) {
                    val itemName = item.name
                    mFbList.add(itemName)
                }
                val filteredList = mFbList.filter {
                    it.contains(love, ignoreCase = true)
                }
                viewModel2.fetchAnime(love)
                viewModel2.listAnimeGif.observe(this, Observer {
                    mAdapter.submitList2(filteredList, it)
                })
            }
        }
    }


    override fun setupListener() {
        super.setupListener()
        mAdapter.setOnClickListener(object : SearchAdapter.onItemClickListener {
            override fun onItemClikc(position: Int) {
                if(position < mAdapter.mlist.size) {
                    val love = mAdapter.mlist[position]
                    val intent = Intent(requireContext(), SetUpActivity::class.java)
                    intent.putExtra("searchWH2", love)
                    startActivity(intent)
                } else {
                    val love = mAdapter.mlist2[position - mAdapter.mlist.size].images.ogImage.url
                    val intent = Intent(requireContext(), SetUpActivity::class.java)
                    intent.putExtra("searchWH", love)
                    startActivity(intent)
                }
            }
        })
    }
}