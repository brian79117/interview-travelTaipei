package com.example.travel_taipei.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val isLoading = MediatorLiveData<Boolean>()
    val errorMsg = MutableLiveData<String>()
}