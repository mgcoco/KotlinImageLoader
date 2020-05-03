package com.cmoney.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cmoney.demo.R
import com.cmoney.demo.base.BaseAdapter
import com.cmoney.demo.base.BaseViewHolder
import com.cmoney.demo.model.CellInfo
import com.cmoney.demo.network.ImageLoader

class CellAdapter : BaseAdapter<CellInfo>(){

    class ViewHolder : BaseViewHolder<CellInfo> {

        var bg: ImageView? = null
        var index: TextView? = null
        var title: TextView? = null
        var onItemClickListener: OnItemClickListener<CellInfo>? = null

        constructor(v : View, listener: OnItemClickListener<CellInfo>) : super(v) {
            bg = v.findViewById(R.id.cell_bg)
            index = v.findViewById(R.id.cell_index)
            title = v.findViewById(R.id.cell_title)
            onItemClickListener = listener
        }

        override fun onBind(position: Int, data: CellInfo) {
            bg!!.setImageBitmap(null)
            ImageLoader().load(bg!!, data.getConvertedThumbnailUrl())
            index!!.text = "" + data.id
            title!!.text = data.title

            bg!!.setOnClickListener {
                onItemClickListener!!.onItemClick(position, data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CellInfo> {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cell, null), mOnItemClickListener!!)

    }

}