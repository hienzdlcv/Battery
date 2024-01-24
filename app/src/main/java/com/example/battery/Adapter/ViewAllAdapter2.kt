package com.example.battery.Adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.battery.R

class ViewAllAdapter2(val context: Context) : RecyclerView.Adapter<ViewAllAdapter2.ViewHolder>() {

    var mList = mutableListOf<String>()
    var hehe : String ?= null

    lateinit var mlistener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listerner: onItemClickListener) {
        mlistener = listerner
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mImg = itemView.findViewById<AppCompatImageView>(R.id.appCompatImageView)

        init {
           itemView.setOnClickListener {
               mlistener.onItemClick(adapterPosition)
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_for_rcv,parent,false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gifs = mList[position]
        val love = "https://firebasestorage.googleapis.com/v0/b/batterycharge-3632b.appspot.com/o/images%2F$hehe%2F$gifs?alt=media"
        Glide.with(context).load(love).into(holder.mImg)
    }
    fun setupHeheValue(love: String) {
        hehe = love
    }

    fun updateData(newImageList: MutableList<String>) {
        mList = newImageList
        notifyDataSetChanged()
    }
}