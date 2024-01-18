package com.example.travel_taipei.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_taipei.MainApplication
import com.example.travel_taipei.R
import com.example.travel_taipei.adapter.NewsAdapter
import com.example.travel_taipei.databinding.FragmentNewsBinding
import com.example.travel_taipei.util.LIST_PAGE_SIZE
import com.example.travel_taipei.viewmodel.NewsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null
    private val newsVM: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)

        Timber.d("onCreateView")

        initNewsList()
        initObservers()

        newsVM.getNewsList(false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated")
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

    private fun initObservers() {
        newsVM.isLoading.observe(viewLifecycleOwner) {
            Timber.d("isLoading = $it")
            if (it) {
                newsAdapter.setLoadingItem()
            } else {
                newsAdapter.removeLoadingItem()
            }
        }

        newsVM.newsListData.observe(viewLifecycleOwner) {
            newsAdapter.setData(it)
        }

        newsVM.errorMsg.observe(viewLifecycleOwner) {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(it)
                .setPositiveButton(MainApplication.appResources.getString(R.string.confirm)) { dialog, _ ->
                    dialog.dismiss()
                }
        }
    }

    private fun initNewsList() {
        binding!!.rvNews.adapter = newsAdapter
        binding!!.rvNews.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding!!.rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager

                if (manager.findLastVisibleItemPosition() == (manager.itemCount - 1) &&
                    !newsVM.isLoading.value!! &&
                    LIST_PAGE_SIZE * newsVM.page < newsVM.totalCount
                ) {
                    newsVM.getNewsList(true)
                }
            }
        })

        newsAdapter.setItemClickable(true)
    }
}