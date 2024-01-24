package com.example.battery.UI.Gallery.TabLayoutFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.battery.Adapter.GalleryAdapter
import com.example.battery.Base.BaseFragment
import com.example.battery.DataBase.MyEntity
import com.example.battery.R
import com.example.battery.UI.Gallery.Crud
import com.example.battery.UI.PhotoPicker.PhotoPickerActivity
import com.example.battery.UI.Setup.SetUpActivity
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.databinding.FragmentBackGroundBinding
import kotlinx.coroutines.launch


class BackGround : BaseFragment<FragmentBackGroundBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_back_ground

    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var viewModel: GalleryViewModel
    private lateinit var db: MyEntity
    private val imageList = mutableListOf<List<MyEntity>>()

    override fun setupUI() {
        super.setupUI()

        binding.rcvGallery.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvGallery.setHasFixedSize(true)
        galleryAdapter = GalleryAdapter(requireContext())
        binding.rcvGallery.adapter = galleryAdapter

        viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        viewModel.theChoosenOne()
        viewModel.chosenPath.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val  love = it
                Log.d("zzz", "setupUI:$love ")
            }
        })

        viewModel.getAll1()
        viewModel.getAll1.observe(viewLifecycleOwner, Observer {
            galleryAdapter.setData(it)
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setupListener() {
        super.setupListener()
        binding.apply {
            fltBtn.setOnClickListener {
                val intent = Intent(requireContext(), PhotoPickerActivity::class.java)
                startActivity(intent)
            }
            deleteBtn.setOnClickListener {
                viewModel.deleteSelectedItem(galleryAdapter.listItem)
            }

            lifecycleScope.launch {
                Crud.edit.flowWithLifecycle(lifecycle).collect{
                    if(it) {
                        deleteBtn.visibility = View.VISIBLE
                        fltBtn.visibility = View.GONE
                        galleryAdapter.turnOnCheckBox = true
                        galleryAdapter.notifyDataSetChanged()
                    }
                    else {
                        galleryAdapter.turnOnCheckBox = false
                        galleryAdapter.notifyDataSetChanged()
                        deleteBtn.visibility = View.GONE
                        fltBtn.visibility = View.VISIBLE
                        galleryAdapter.unChooseAll()
                    }
                }
            }

            lifecycleScope.launch {
                Crud.chooseAll.flowWithLifecycle(lifecycle).collect{
                    if (it) {
                        galleryAdapter.chooseAll()
                    }
                    else {
                        galleryAdapter.unChooseAll()
                    }
                }
            }
        }
        galleryAdapter.setOnItemClickListener(object : GalleryAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val selectedItem = galleryAdapter.listItem[position].path
                val intent = Intent(requireContext(), SetUpActivity::class.java)
                intent.putExtra("entity",selectedItem)
                startActivity(intent)
            }
        })
    }

}