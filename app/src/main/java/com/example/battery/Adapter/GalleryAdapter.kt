package com.example.battery.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.battery.DataBase.MyEntity
import com.example.battery.R

class GalleryAdapter(val context: Context) : RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {

     var listItem = arrayListOf<MyEntity>()
    var turnOnCheckBox = false
    private var mListener: OnClickListener ?= null

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: GalleryAdapter.OnClickListener) {
        mListener = listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mIMG = itemView.findViewById<AppCompatImageView>(R.id.appCompatImageView)
        val isUnChecked = itemView.findViewById<ImageView>(R.id.unchecked)

        init {
            itemView.setOnClickListener {
                mListener?.onItemClick(adapterPosition)
            }
        }

        fun checkBox(data: MyEntity) {
            if(turnOnCheckBox) {
                isUnChecked.visibility = View.VISIBLE
                isUnChecked.setImageResource(if (data.isSelected) R.drawable.ic_checkbox else R.drawable.ic_uncheckbox)
                isUnChecked.setOnClickListener {
                    data.isSelected = !data.isSelected
                    notifyItemChanged(position)
                    notifyDataSetChanged()
                }
            } else {
                isUnChecked.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_for_rcv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GalleryAdapter.MyViewHolder, position: Int) {
        val currentItem = listItem[position]
        //Picasso.get().load(currentItem.path).into(holder.mIMG)
        Glide.with(context).load(currentItem.path).into(holder.mIMG)

        holder.checkBox(currentItem)

    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setData(entity: LiveData<List<MyEntity>>) {
        entity.observeForever{
            it?.let{
                listItem.clear()
                listItem.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    fun chooseAll() {
        listItem.forEach {
            it.isSelected = true
            notifyDataSetChanged()
        }
    }

    fun unChooseAll() {
        listItem.forEach {
            it.isSelected = false
            notifyDataSetChanged()
        }
    }
}