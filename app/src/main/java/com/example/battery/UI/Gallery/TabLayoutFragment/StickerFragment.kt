package com.example.battery.UI.Gallery.TabLayoutFragment

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.battery.Adapter.StickerAdapter
import com.example.battery.Base.BaseFragment
import com.example.battery.R
import com.example.battery.UI.Gallery.Crud
import com.example.battery.UI.Setup.SetUpActivity
import com.example.battery.ViewModel.GalleryViewModel
import com.example.battery.databinding.FragmentStickerBinding
import kotlinx.coroutines.launch

class StickerFragment : BaseFragment<FragmentStickerBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_sticker

    private lateinit var mAdapter: StickerAdapter
    private lateinit var viewModel: GalleryViewModel

    override fun setupUI() {
        super.setupUI()

        viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        binding.apply {
            rcvSticker.layoutManager = GridLayoutManager(requireContext(), 2)
            rcvSticker.setHasFixedSize(true)
            mAdapter = StickerAdapter(requireContext())
            rcvSticker.adapter = mAdapter
        }

        viewModel.theChoosenSticker()
        viewModel.choosenSticker.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val love = it
                Log.d("zzz", "choosenSticker: $love ")
            }
        })

        viewModel.fetchStickerData()
        viewModel.readAllSticker.observe(viewLifecycleOwner, Observer {
            mAdapter.setData(it)
        })
    }

    override fun setupListener() {
        super.setupListener()
        mAdapter.setOnItemClickListener(object : StickerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val selectedItem = mAdapter.mList[position]?.sticker
                val intent = Intent(requireContext(), SetUpActivity::class.java)
                intent.putExtra("stickerGlr", selectedItem)
                startActivity(intent)
            }
        })

        binding.apply {
            deleteBtnSticker.setOnClickListener {
                viewModel.deleteChoosenSticker(mAdapter.mList)
                viewModel.readAllSticker.observe(viewLifecycleOwner, Observer {
                    mAdapter.setData(it)
                })
                mAdapter.notifyDataSetChanged()
            }
            lifecycleScope.launch {
                Crud.edit.flowWithLifecycle(lifecycle).collect{
                    if(it) {
                        deleteBtnSticker.visibility = View.VISIBLE
                        mAdapter.turnOnCheckBoxSticker = true
                        mAdapter.notifyDataSetChanged()
                    }
                    else {
                        mAdapter.turnOnCheckBoxSticker = false
                        mAdapter.notifyDataSetChanged()
                        deleteBtnSticker.visibility = View.GONE
                    }
                }
            }
            lifecycleScope.launch{
                Crud.chooseAll.flowWithLifecycle(lifecycle).collect{
                    if (it) {
                        mAdapter.chooseAll()
                    }
                    else {
                        mAdapter.unChooseAll()
                    }
                }
            }
        }
    }
}