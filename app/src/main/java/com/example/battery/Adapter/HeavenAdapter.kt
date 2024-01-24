package com.example.battery.Adapter

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.battery.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HeavenAdapter(private val context: Context, var imageList: List<String>) :
    RecyclerView.Adapter<HeavenAdapter.ViewHolder>() {

    lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.animation)
        init {
            itemView.setOnClickListener {
                mListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_home,parent,false))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gifs = imageList[position]
        val love = "https://firebasestorage.googleapis.com/v0/b/batterycharge-3632b.appspot.com/o/images%2F${gifs}?alt=media"
        Glide.with(context).load(love).into(holder.imageView)
        val downloadId = DownloadManager.Request(love.toUri())
        downloadId
        Log.d("clgt", "onBindViewHolder: $downloadId")

    }
    fun updateData(newImageList: List<String>) {
        imageList = newImageList
        notifyDataSetChanged()
    }

}