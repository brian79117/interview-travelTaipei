package com.example.travel_taipei.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_taipei.MainApplication.Companion.appResources
import com.example.travel_taipei.R
import com.example.travel_taipei.api.datamodel.News
import com.example.travel_taipei.api.datamodel.WebView
import com.example.travel_taipei.databinding.ItemLoadingBinding
import com.example.travel_taipei.databinding.ItemNewsBinding
import com.example.travel_taipei.ui.fragment.MainFragmentDirections
import com.example.travel_taipei.util.VIEW_TYPE_ITEM
import com.example.travel_taipei.util.VIEW_TYPE_LOADING

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val newsData = ArrayList<News?>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        if (viewType == VIEW_TYPE_ITEM) {
            return NewsViewHolder(
                ItemNewsBinding.inflate(
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

    override fun getItemCount(): Int = newsData.size

    override fun getItemViewType(position: Int): Int {
        return if (newsData[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            holder.title.text = newsData[position]?.title
            holder.content.text =
                Html.fromHtml(newsData[position]?.description, Html.FROM_HTML_MODE_LEGACY)

            if (!newsData[position]?.url.isNullOrBlank()) {
                holder.item.setOnClickListener { view ->
                    val parameters = WebView(
                        appResources.getString(R.string.title_news),
                        newsData[position]!!.url
                    )

                    val action =
                        MainFragmentDirections.actionMainFragmentToWebViewFragment(parameters)

                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }

    fun setData(data: ArrayList<News?>) {
        val startPos = newsData.size
        val insertCount = data.size
        newsData.addAll(data)
        this.notifyItemRangeInserted(startPos, insertCount)
    }

    fun setLoadingItem() {
        if (newsData.size != 0 && newsData.last() == null) {
            return
        }
        val list: ArrayList<News?> = arrayListOf(null)
        newsData.addAll(list)
        this.notifyItemInserted(newsData.size)
    }

    fun removeLoadingItem() {
        if (newsData.size == 0 || newsData.last() != null) {
            return
        }
        this.notifyItemRemoved(newsData.size - 1)
        newsData.removeLast()
    }

    inner class NewsViewHolder(view: ItemNewsBinding) : RecyclerView.ViewHolder(view.root) {
        val title = view.txvTitle
        val content = view.txvContent
        val item = view.mcvNews
    }

    inner class LoadingViewHolder(view: ItemLoadingBinding) : RecyclerView.ViewHolder(view.root)
}