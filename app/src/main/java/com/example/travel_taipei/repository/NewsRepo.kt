package com.example.travel_taipei.repository

import androidx.lifecycle.MutableLiveData
import com.example.travel_taipei.api.Apis
import com.example.travel_taipei.api.datamodel.ApiResponse
import com.example.travel_taipei.api.datamodel.ListResp
import com.example.travel_taipei.api.datamodel.News
import com.example.travel_taipei.api.errorMsg
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NewsRepo @Inject constructor(private val apiService: Apis) {

    val newsResp = MutableLiveData<ApiResponse<ListResp<News?>>>()

    fun getNewsList(lang: String, page: Int) = apiService
        .getNewsList(lang = lang, page = page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            newsResp.postValue(ApiResponse.success(it))
        }) {
            newsResp.postValue(ApiResponse.error(it.errorMsg()))
        }
}