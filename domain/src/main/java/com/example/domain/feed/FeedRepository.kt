package com.example.domain.feed

import com.example.model.FeedData
import com.example.model.FeedListData
import com.example.model.Feed
import io.reactivex.Single
import com.example.domain.result.Result
import com.example.model.PostFeedParameter

interface FeedRepository {
    fun getFeedList(getFeedParameter: GetFeedParameter) : Single<Result<FeedListData>>
    fun saveFeedList(feedList: ArrayList<Feed>) : Single<Result<ArrayList<Long>>>
    fun deleteAll() : Single<Result<Int>>
    fun deleteFeed(id: Int) : Single<Result<String>>
    fun getFeedData(parameter:Int): Single<Result<FeedData>>
    fun postFeed(postFeedParameter: PostFeedParameter) : Single<Result<String>>
}