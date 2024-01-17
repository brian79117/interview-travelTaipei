package com.example.travel_taipei.api.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebView(
    val title: String,
    val url: String
) : Parcelable
