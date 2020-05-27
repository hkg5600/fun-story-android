package com.example.data.feed

import com.example.model.FeedData
import com.example.model.FeedListData
import com.example.model.Response
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface FeedApi {

    @GET
    fun getFeedDetail(@Url url: String): Single<retrofit2.Response<Response<FeedData>>>

    @GET
    fun getFeed(
        @Url url: String
    ): Single<retrofit2.Response<Response<FeedListData>>>
}