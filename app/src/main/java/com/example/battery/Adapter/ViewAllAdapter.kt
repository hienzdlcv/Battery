package com.example.battery.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.battery.R
import com.example.battery.Api.ApiFromGifphy.Gif
import com.example.battery.Api.ApiFromGifphy.ImageAnime

class ViewAllAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mList = mutableListOf<Gif>()
    val mList2 = mutableListOf<ImageAnime>()
    val mlist3 = mutableListOf<String>()

    lateinit var mListener1: onItemClickListener

    private val VIEW_TYPE_1 = 1
    private val VIEW_TYPE_2 = 2

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener: onItemClickListener) {
        mListener1 = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < mList.size) VIEW_TYPE_1 else VIEW_TYPE_2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_1 -> ViewAllAdapter.ViewHolderType1(
                LayoutInflater.from(parent.context).inflate(R.layout.item_for_rcv, parent, false),
                mListener1
            )

            VIEW_TYPE_2 -> ViewAllAdapter.ViewHolderType2(
                LayoutInflater.from(parent.context).inflate(R.layout.item_for_rcv2, parent, false),
                mListener1
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_1 -> (holder as ViewAllAdapter.ViewHolderType1).bind(mList[position])
            VIEW_TYPE_2 -> (holder as ViewAllAdapter.ViewHolderType2).bind(mList2[position - mList.size])
        }
    }

    override fun getItemCount(): Int {
        return mList.size + mList2.size
    }

    fun submitList(newList: List<Gif>) {
        mList.clear()
        mList.addAll(newList)
        notifyDataSetChanged()
    }

    fun submitList2(newList: List<ImageAnime>) {
        mList2.clear()
        mList2.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolderType1(itemView: View, listener: ViewAllAdapter.onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val mTxt = itemView.findViewById<AppCompatImageView>(R.id.appCompatImageView)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(item: Gif) {
            Glide.with(itemView).load(item.images.still480w.url).into(mTxt)
        }
    }

    class ViewHolderType2(itemView: View, listener: ViewAllAdapter.onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val mTxt = itemView.findViewById<AppCompatImageView>(R.id.appCompatImageView2)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(item: ImageAnime) {
            Glide.with(itemView).load(item.images.ogImage.url).into(mTxt)
        }
    }
}