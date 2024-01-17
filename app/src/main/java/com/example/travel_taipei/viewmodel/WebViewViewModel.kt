package com.example.travel_taipei.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.travel_taipei.api.datamodel.WebView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(state: SavedStateHandle) : ViewModel() {
    val params = state.get<WebView>("webParameters")
}