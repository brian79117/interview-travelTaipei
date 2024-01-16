package com.example.travel_taipei.api

import com.example.travel_taipei.api.datamodel.Attractions
import com.example.travel_taipei.api.datamodel.ListResp
import com.example.travel_taipei.api.datamodel.News
import com.example.travel_taipei.util.ACCEPT_JSON
import com.example.travel_taipei.util.HEAD_ACCEPT
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Apis {
    @GET("{lang}/Attractions/All")
    fun getAttractionsList(
        @Header(HEAD_ACCEPT) accept: String = ACCEPT_JSON,
        @Path("lang") lang: String,
        @Query("page") page: Int
    ): Observable<ListResp<Attractions?>>

    @GET("{lang}/Events/News")
    fun getNewsList(
        @Header(HEAD_ACCEPT) accept: String = ACCEPT_JSON,
        @Path("lang") lang: String,
        @Query("page") page: Int
    ): Observable<ListResp<News?>>
}