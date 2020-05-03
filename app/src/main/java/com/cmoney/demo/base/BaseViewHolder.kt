package com.cmoney.demo.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun onBind(position : Int, data : T)
}