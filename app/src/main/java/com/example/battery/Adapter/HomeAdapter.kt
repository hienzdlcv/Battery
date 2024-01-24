package com.example.battery.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.battery.Api.ApiFromGifphy.DataImg
import com.example.battery.Api.ApiFromGifphy.DataObject
import com.example.battery.R
import kotlin.math.min

class HomeAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mList= mutableListOf<DataObject>()
    val mlist2= mutableListOf<DataImg>()
    lateinit var mListener: OnItemClickListener

    private val VIEW_TYPE_1 = 1
    private val VIEW_TYPE_2 = 2


    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < mList.size) VIEW_TYPE_1 else VIEW_TYPE_2
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_1 -> HomeAdapter.ViewHolderType1(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rcv_home, parent, false), mListener
            )
            VIEW_TYPE_2 -> HomeAdapter.ViewHolderType2(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rcv_home2, parent, false), mListener
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun getItemCount(): Int {
        val maxSize = 15
        val sizeList1 = min(mList.size, maxSize)
        val sizeList2 = min(mlist2.size, maxSize)

        return sizeList1 + sizeList2
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_1 -> (holder as ViewHolderType1).bind(mList[position])
            VIEW_TYPE_2 -> (holder as ViewHolderType2).bind(mlist2[position - mList.size])
        }
    }

    fun submitList(newList: List<DataObject>) {
        mList.clear()
        mList.addAll(newList)
        notifyDataSetChanged()
    }

    fun submitList2(newList: List<DataImg>) {
        mlist2.clear()
        mlist2.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolderType1(itemView: View,listener: HomeAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val mTxt = itemView.findViewById<AppCompatImageView>(R.id.animation)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
        fun bind(item: DataObject) {
            Glide.with(itemView).load(item.images.ogImage.url).into(mTxt)
        }
    }

    class ViewHolderType2(itemView: View,listener: HomeAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val mTxt = itemView.findViewById<AppCompatImageView>(R.id.animation2)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
        fun bind(item: DataImg) {
            Glide.with(itemView).load(item.image.dataOri.url).into(mTxt)
        }
    }
}