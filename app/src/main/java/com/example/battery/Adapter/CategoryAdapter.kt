package com.example.battery.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.battery.R
import com.example.battery.Api.ApiFromGifphy.NameCate
import com.example.battery.Api.ApiFromGifphy.SubNameCate

class CategoryAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mList= mutableListOf<NameCate>()
    val mList2= mutableListOf<SubNameCate>()

    private val VIEW_TYPE_1 = 1
    private val VIEW_TYPE_2 = 2

    lateinit var mListener1: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener1(listener: OnItemClickListener) {
        mListener1 = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < mList.size) VIEW_TYPE_1 else VIEW_TYPE_2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_1 -> ViewHolderType1(LayoutInflater.from(parent.context).inflate(R.layout.item_for_category, parent, false),mListener1)
            VIEW_TYPE_2 -> ViewHolderType2(LayoutInflater.from(parent.context).inflate(R.layout.item_for_category2, parent, false),mListener1)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return mList.size + mList2.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_1 -> (holder as ViewHolderType1).bind(mList[position])
            VIEW_TYPE_2 -> (holder as ViewHolderType2).bind(mList2[position - mList.size])
        }
    }

    fun submitList(newList: List<NameCate>) {
        mList.clear()
        mList.addAll(newList)
        notifyDataSetChanged()
    }

    fun submitList2(newList: List<SubNameCate>) {
        mList2.clear()
        mList2.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolderType1(itemView: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val mTxt = itemView.findViewById<TextView>(R.id.txtCate)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
        fun bind(item: NameCate) {
            mTxt.text = item.name
        }
    }

    class ViewHolderType2(itemView: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val mTxt = itemView.findViewById<TextView>(R.id.txtCate2)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
        fun bind(item: SubNameCate) {
            mTxt.text = item.name
        }
    }
}