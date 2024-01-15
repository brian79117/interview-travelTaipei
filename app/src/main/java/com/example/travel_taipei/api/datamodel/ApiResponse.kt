package com.example.travel_taipei.api.datamodel

import com.example.travel_taipei.enum.ApiStatus

data class ApiResponse<T>(
    val status: ApiStatus,
    val result: T?,
    val message: String?
) {
    companion object {

        fun <T> success(data: T?): ApiResponse<T> {
            return ApiResponse(ApiStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String): ApiResponse<T> {
            return ApiResponse(ApiStatus.ERROR, null, msg)
        }

        fun <T> loading(): ApiResponse<T> {
            return ApiResponse(ApiStatus.LOADING, null, null)
        }

    }
}
