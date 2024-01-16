package com.example.travel_taipei.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.travel_taipei.api.datamodel.ApiResponse
import com.example.travel_taipei.api.datamodel.News
import com.example.travel_taipei.enum.ApiStatus
import com.example.travel_taipei.repository.NewsRepo
import com.example.travel_taipei.util.getApiLang
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repo: NewsRepo
) : BaseViewModel() {

    val newsListData = MutableLiveData<ArrayList<News?>>()
    var page = 0
    var totalCount = 0

    init {
        isLoading.addSource(repo.newsResp) { resp ->
            when (resp.status) {
                ApiStatus.LOADING -> isLoading.postValue(true)
                ApiStatus.SUCCESS -> {
                    isLoading.postValue(false)
                    totalCount = resp.result!!.total
                    newsListData.postValue(resp.result.data)
                }

                ApiStatus.ERROR -> {
                    isLoading.postValue(false)
                    errorMsg.postValue(resp.message)
                }
            }
        }
    }

    fun getNewsList() {
        page += 1
        repo.newsResp.postValue(ApiResponse.loading())
        repo.getNewsList(getApiLang(appContext), page)
    }
}