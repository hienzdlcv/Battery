package com.example.battery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.battery.Api.ApiFromWallHeaven.DataCate

class CategoryAdapter2( val mList : ArrayList<DataCate>): RecyclerView.Adapter<CategoryAdapter2.ViewHolder>() {

   // val mList = mutableListOf<DataCate>()
    lateinit var mlistener: onItemClickListener

    interface onItemClickListener{
        fun OnItemClick(position: Int)
    }

    fun SetOnItemClickListener(listener: onItemClickListener){
        mlistener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mtxt = itemView.findViewById<TextView>(R.id.txtCate)

        init {
            itemView.setOnClickListener {
                mlistener.OnItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_for_category,parent,false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gift = mList[position]
        holder.mtxt.text = gift.text
    }
}