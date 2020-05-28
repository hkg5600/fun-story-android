package com.example.domain.save

import com.example.domain.result.Result
import com.example.model.Feed
import com.example.model.FeedListData
import com.example.model.SaveListData
import io.reactivex.Single

interface SaveRepository {
    fun getSavedFeedList(): Single<Result<SaveListData>>
    fun saveFeed(entity: Feed): Single<Result<Long>>
    fun saveFeedList(feedList: ArrayList<Int>) : Single<Result<String>>
    fun deleteFeed(id: Int) : Single<Result<Int>>
}