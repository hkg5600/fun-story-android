package com.example.data.feed

import com.example.model.FeedData
import com.example.model.FeedListData
import com.example.model.PostFeedParameter
import com.example.model.Response
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.*

interface FeedApi {

    @GET
    fun getFeedDetail(@Url url: String): Single<retrofit2.Response<Response<FeedData>>>

    @GET
    fun getFeed(
        @Url url: String
    ): Single<retrofit2.Response<Response<FeedListData>>>

    @DELETE
    fun deleteFeed(
        @Url url: String
    ) : Single<retrofit2.Response<Response<String>>>

    @POST("/api/write-story/")
    fun postFeed(
        @Body postFeedParameter: PostFeedParameter
    ) : Single<retrofit2.Response<Response<String>>>
}