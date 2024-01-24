package com.example.battery.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.battery.Api.ApiFromGifphy.ImageAnime
import com.example.battery.R

class SearchAdapter(val context: Context) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    val mlist = mutableListOf<String>()
    val mlist2 = mutableListOf<ImageAnime>()
    var mTotal = mutableListOf<String>()


    lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClikc(position: Int)
    }

    fun setOnClickListener(listtener: onItemClickListener) {
        mListener = listtener
    }

    inner class ViewHolder(val item: View, mListener: onItemClickListener) : RecyclerView.ViewHolder(item) {
        val mImg = item.findViewById<AppCompatImageView>(R.id.appCompatImageView)
        init {
            item.setOnClickListener {
                mListener.onItemClikc(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_for_rcv,parent,false),mListener)
    }

    override fun getItemCount(): Int {
        return mlist.size + mlist2.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < mlist.size) {
            val gifs1 = mlist[position]
            val love = "https://firebasestorage.googleapis.com/v0/b/batterycharge-3632b.appspot.com/o/images%2F${gifs1}?alt=media"
            Glide.with(context).load(love).into(holder.mImg)
        } else {
            val gifs2 = mlist2[position - mlist.size]
            Glide.with(context).load(gifs2.images.ogImage.url).into(holder.mImg)
        }
    }

    fun submitList2(newList: List<String>,newList2: List<ImageAnime>) {
        mlist.clear()
        mlist2.clear()
        mlist2.addAll(newList2)
        mlist.addAll(newList)
        notifyDataSetChanged()
    }
}