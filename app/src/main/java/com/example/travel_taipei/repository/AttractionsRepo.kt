package com.example.travel_taipei.repository

import androidx.lifecycle.MutableLiveData
import com.example.travel_taipei.api.Apis
import com.example.travel_taipei.api.datamodel.ApiResponse
import com.example.travel_taipei.api.datamodel.Attractions
import com.example.travel_taipei.api.datamodel.ListResp
import com.example.travel_taipei.api.errorMsg
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class AttractionsRepo @Inject constructor(private val apiService: Apis) {

    val attractionsResp = MutableLiveData<ApiResponse<ListResp<Attractions>>>()

    fun getAttractionsList(lang: String, page: Int): Disposable = apiService
        .getAttractionsList(lang = lang, page = page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            attractionsResp.postValue(ApiResponse.success(it))
        }) {
            attractionsResp.postValue(ApiResponse.error(it.errorMsg()))
        }
}