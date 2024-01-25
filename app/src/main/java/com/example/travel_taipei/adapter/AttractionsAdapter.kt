package com.example.travel_taipei.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travel_taipei.R
import com.example.travel_taipei.api.datamodel.Attractions
import com.example.travel_taipei.api.datamodel.AttractionsDetail
import com.example.travel_taipei.databinding.ItemAttractionsBinding
import com.example.travel_taipei.databinding.ItemLoadingBinding
import com.example.travel_taipei.ui.fragment.MainFragmentDirections
import com.example.travel_taipei.util.VIEW_TYPE_ITEM
import com.example.travel_taipei.util.VIEW_TYPE_LOADING
import timber.log.Timber

class AttractionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val attractionsData = ArrayList<Attractions?>()
    private lateinit var context: Context
    private var isItemClickable = true

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
            holder.content.text =
                Html.fromHtml(attractionsData[position]?.introduction, Html.FROM_HTML_MODE_LEGACY)
            Glide.with(context)
                .load(if (attractionsData[position]?.images?.size == 0) null else attractionsData[position]!!.images[0].src)
                .centerInside()
                .error(AppCompatResources.getDrawable(context, R.drawable.default_image))
                .into(holder.image)

            holder.item.setOnClickListener { view ->
                if (isItemClickable) {
                    isItemClickable = false
                    val parameters = AttractionsDetail(
                        attractionsData[position]!!.name,
                        attractionsData[position]!!.open_time,
                        attractionsData[position]!!.address,
                        attractionsData[position]!!.tel,
                        attractionsData[position]!!.official_site,
                        attractionsData[position]!!.introduction,
                        ArrayList(attractionsData[position]!!.images.map { it.src })
                    )

                    val action =
                        MainFragmentDirections.actionMainFragmentToAttractionsDetailFragment(
                            parameters
                        )
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int = attractionsData.size

    override fun getItemViewType(position: Int): Int {
        return if (attractionsData[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setData(data: ArrayList<Attractions?>) {
        if (data.lastOrNull() == attractionsData.lastOrNull())
            return
        val startPos = attractionsData.size
        val insertCount = data.size
        attractionsData.addAll(data)
        this.notifyItemRangeInserted(startPos, insertCount)
        Timber.d(">>> setData: ${attractionsData.size}")
    }

    fun setLoadingItem() {
        if (attractionsData.size != 0 && attractionsData.last() == null) {
            return
        }
        val list: ArrayList<Attractions?> = arrayListOf(null)
        attractionsData.addAll(list)
        this.notifyItemInserted(attractionsData.size)
    }

    fun removeLoadingItem() {
        if (attractionsData.size == 0 || attractionsData.last() != null) {
            return
        }
        this.notifyItemRemoved(attractionsData.size - 1)
        attractionsData.removeLast()
    }

    fun setItemClickable(isClickable: Boolean) {
        isItemClickable = isClickable
    }

    inner class AttractionsViewHolder(view: ItemAttractionsBinding) :
        RecyclerView.ViewHolder(view.root) {
        val title = view.txvTitle
        val content = view.txvContent
        val image = view.ivMain
        val item = view.mcvAttractions
    }

    inner class LoadingViewHolder(view: ItemLoadingBinding) : RecyclerView.ViewHolder(view.root)

}