package com.example.travel_taipei.api

import com.example.travel_taipei.MainApplication.Companion.appResources
import com.example.travel_taipei.R
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.errorMsg(): String = when (this) {
    is ConnectException -> appResources.getString(R.string.api_no_network)
    is UnknownHostException -> appResources.getString(R.string.api_unknown_host_error)
    is HttpException -> getMsgByErrorCode(this)
    else -> appResources.getString(R.string.api_unknown_error)
}

private fun getMsgByErrorCode(e: HttpException): String = when (e.code()) {
    403 -> appResources.getString(R.string.api_403_error)
    404 -> appResources.getString(R.string.api_404_error)
    500 -> appResources.getString(R.string.api_500_error)
    else -> appResources.getString(R.string.api_unknown_error) + ", ${e.code()}"
}
