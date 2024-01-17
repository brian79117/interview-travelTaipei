package com.example.travel_taipei.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_taipei.R
import com.example.travel_taipei.databinding.ItemIndicatorBinding

class IndicatorAdapter : RecyclerView.Adapter<IndicatorAdapter.ViewHolder>(){

    private lateinit var context: Context
    private var bannerLength = 0
    private var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemIndicatorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == currentPosition)
            holder.indicator.background =
                context.resources.getDrawable(R.drawable.indecator_choose, context.theme)
        else
            holder.indicator.background =
                context.resources.getDrawable(R.drawable.indicator_default, context.theme)
    }

    override fun getItemCount(): Int = bannerLength

    fun setLength(length: Int) {
        bannerLength = length
        this.notifyDataSetChanged()
    }

    fun setCurrentPos(pos: Int) {
        currentPosition = pos
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(view: ItemIndicatorBinding) : RecyclerView.ViewHolder(view.root) {
        val indicator = view.vIndicator
    }
}