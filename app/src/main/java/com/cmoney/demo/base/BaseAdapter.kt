package com.cmoney.demo.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>(){

    val mData: ArrayList<T> = arrayListOf()

    open var mOnItemClickListener: OnItemClickListener<T>? = null

    interface OnItemClickListener<T>{
        fun onItemClick(position: Int, data : T)
    }

    override fun getItemCount(): Int {
        if(mData != null){
            return mData.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(position, mData!!.get(position))
        holder.itemView.setOnClickListener {
            mOnItemClickListener!!.onItemClick(position, mData.get(position));
        }
    }


    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        mOnItemClickListener = listener
    }

    open fun addItems(list: ArrayList<T>) {
        mData.addAll(list)
        notifyDataSetChanged()
    }

    open fun addItem(data: T) {
        mData.add(data)
        notifyDataSetChanged()
    }

}