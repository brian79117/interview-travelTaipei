package com.example.travel_taipei.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.travel_taipei.api.datamodel.ApiResponse
import com.example.travel_taipei.api.datamodel.Attractions
import com.example.travel_taipei.enum.ApiStatus
import com.example.travel_taipei.repository.AttractionsRepo
import com.example.travel_taipei.util.getApiLang
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AttractionsViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repo: AttractionsRepo
) : BaseViewModel() {

    val attractionsListData = MutableLiveData<ArrayList<Attractions?>>()
    var page = 0
    var totalCount = 0

    init {
        isLoading.addSource(repo.attractionsResp) { resp ->
            when (resp.status) {
                ApiStatus.LOADING -> isLoading.postValue(true)
                ApiStatus.SUCCESS -> {
                    isLoading.postValue(false)
                    totalCount = resp.result!!.total
                    attractionsListData.postValue(resp.result.data)
                }

                ApiStatus.ERROR -> {
                    isLoading.postValue(false)
                    errorMsg.postValue(resp.message)
                }
            }
        }
    }

    fun getAttractionsList() {
        page += 1
        repo.attractionsResp.postValue(ApiResponse.loading())
        repo.getAttractionsList(getApiLang(appContext), page)
    }
}