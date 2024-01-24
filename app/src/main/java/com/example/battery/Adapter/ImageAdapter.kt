package com.example.battery.Adapter

// ImageAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.battery.R
import com.example.battery.UI.PhotoPicker.ImageModel
import com.squareup.picasso.Picasso

class ImageAdapter(private val imageList: List<ImageModel>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var mListener: onItemClickListener ?= null

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImg = itemView.findViewById<ImageView>(R.id.gridImageView)

        init {
            itemView.setOnClickListener {
                mListener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_for_photopicker, parent,false))
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        val currentItem = imageList[position]
        Picasso.get().load(currentItem.contentUri).into(holder.mImg)
        if (currentItem.isSelected == true ) {
            holder.itemView.setBackgroundResource(R.drawable.bg_item_selected)
        } else {
            holder.itemView.background = null
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}


