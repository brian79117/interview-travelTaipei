package com.example.travel_taipei.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travel_taipei.R
import com.example.travel_taipei.databinding.ItemBannerBinding

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var images = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(images[position])
            .centerInside()
            .error(AppCompatResources.getDrawable(context, R.drawable.default_image))
            .into(holder.image)
    }

    override fun getItemCount(): Int = images.size

    fun setData(data: ArrayList<String>) {
        images = data
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(view: ItemBannerBinding) : RecyclerView.ViewHolder(view.root) {
        val image = view.ivBanner
    }
}

