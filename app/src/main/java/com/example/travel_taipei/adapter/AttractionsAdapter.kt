package com.example.travel_taipei.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travel_taipei.R
import com.example.travel_taipei.api.datamodel.Attractions
import com.example.travel_taipei.databinding.ItemAttractionsBinding
import com.example.travel_taipei.databinding.ItemLoadingBinding
import com.example.travel_taipei.util.VIEW_TYPE_ITEM
import com.example.travel_taipei.util.VIEW_TYPE_LOADING

class AttractionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val attractionsData = ArrayList<Attractions?>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        if (viewType == VIEW_TYPE_ITEM) {
            return AttractionsViewHolder(
                ItemAttractionsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return LoadingViewHolder(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is AttractionsViewHolder) {
            holder.title.text = attractionsData[position]?.name
            holder.content.text = attractionsData[position]?.introduction
            Glide.with(context)
                .load(if (attractionsData[position]?.images?.size == 0) null else attractionsData[position]!!.images[0].src)
                .centerInside()
                .error(context.getDrawable(R.drawable.default_image))
                .into(holder.image)
        }
    }

    override fun getItemCount(): Int = attractionsData.size

    override fun getItemViewType(position: Int): Int {
        return if (attractionsData[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setData(data: ArrayList<Attractions?>) {
        val startPos = if (attractionsData.size == 0) 0 else attractionsData.size
        val insertCount = data.size
        attractionsData.addAll(data)
        this.notifyItemRangeInserted(startPos, insertCount)
    }

    fun setLoadingItem() {
        if (attractionsData.size != 0 && attractionsData.last() == null) {
            return
        }
        val list: ArrayList<Attractions?> = arrayListOf(null)
        attractionsData.addAll(list)
        this.notifyItemInserted(if (attractionsData.size == 0) 0 else attractionsData.size)
    }

    fun removeLoadingItem() {
        if (attractionsData.last() != null) {
            return
        }
        this.notifyItemRemoved(attractionsData.size - 1)
        attractionsData.removeLast()
    }

    inner class AttractionsViewHolder(view: ItemAttractionsBinding) :
        RecyclerView.ViewHolder(view.root) {
        val title = view.txvTitle
        val content = view.txvContent
        val image = view.ivMain
    }

    inner class LoadingViewHolder(view: ItemLoadingBinding) : RecyclerView.ViewHolder(view.root) {

    }
}