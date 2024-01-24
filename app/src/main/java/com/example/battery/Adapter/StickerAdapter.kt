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
import kotlin.collections.ArrayList

class StickerAdapter(val context: Context) : RecyclerView.Adapter<StickerAdapter.ViewHolder>() {

    var mList = arrayListOf<MyEntity>()
    var turnOnCheckBoxSticker = false
    private var mListener: OnClickListener? = null

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: StickerAdapter.OnClickListener) {
        mListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mSticker = itemView.findViewById<AppCompatImageView>(R.id.appCompatImageView)
        val isUnChecked = itemView.findViewById<ImageView>(R.id.unchecked)

        init {
            itemView.setOnClickListener {
                mListener?.onItemClick(adapterPosition)
            }
        }

        fun checkBox(data: MyEntity?) {
            if (turnOnCheckBoxSticker) {
                isUnChecked.visibility = View.VISIBLE
                isUnChecked.setImageResource(if (data!!.isChoosen) R.drawable.ic_checkbox else R.drawable.ic_uncheckbox)
                isUnChecked.setOnClickListener {
                    data.isChoosen = !data.isChoosen
                    notifyItemChanged(position)
                    notifyDataSetChanged()
                }
            } else {
                isUnChecked.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_for_rcv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StickerAdapter.ViewHolder, position: Int) {
        val currentItem = mList[position]
        Glide.with(context).load(currentItem?.sticker).into(holder.mSticker)
        holder.checkBox(currentItem)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(entity: LiveData<List<MyEntity>>) {
        entity.observeForever{
            it?.let{
                mList.clear()
                mList.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    fun chooseAll() {
        mList.forEach {
            it?.isChoosen = true
            notifyDataSetChanged()
        }
    }
    fun unChooseAll() {
        mList.forEach {
            it?.isChoosen = false
            notifyDataSetChanged()
        }
    }


}