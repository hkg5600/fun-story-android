package com.example.data.feed

import com.example.model.FeedData
import com.example.model.FeedListData
import com.example.model.Response
import io.reactivex.Flowable
import io.reactivex.Single

class FeedRemoteDataSource(private val api: FeedApi) {

    fun getFeed(
        page: Int,
        userId: Int = 0,
        isFollowing: Int = 0,
        category: String
    ): Single<retrofit2.Response<Response<FeedListData>>> {
        return api.getFeed("/api/story/$page/$userId/$isFollowing/$category/")
    }

    fun getFeedDetail(
        id: Int
    ) : Single<retrofit2.Response<Response<FeedData>>> {
        return api.getFeedDetail("/api/story-detail/$id/")
    }

    fun deleteFeed(
        id: Int
    ) : Single<retrofit2.Response<Response<String>>> {
        return api.deleteFeed("/api/story/$id/")
    }
}