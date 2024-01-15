package com.example.travel_taipei.api.datamodel

data class ListResp<T> (val total: Int, val data: ArrayList<T>)