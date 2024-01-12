package com.example.travel_taipei.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.travel_taipei.util.getAppCountryCode
import com.example.travel_taipei.util.setAppLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext private val appContext: Context) :
    ViewModel() {
    var checkedItem = 0

    fun getCheckedLanguage(): Int = getAppCountryCode(appContext).ordinal

    fun setLanguage() {
        setAppLanguage(appContext, checkedItem)
    }
}