package com.example.data.feed

import com.example.data.entity.FeedEntity
import com.example.domain.result.Result
import com.example.model.Feed
import com.example.model.FeedData
import com.example.model.FeedListData

object FeedLocalMapper {
    fun mapToData(data: List<FeedEntity>) : Result<FeedListData> {
        val feedList = ArrayList(data.map {
            Feed(it.id, it.title, it.description, it.user, 0, it.category)
        }).apply { reverse() }
        return Result.Success(FeedListData(feedList, true, 0))
    }

    fun mapToData(data: FeedEntity) : Result<FeedData> {
        return Result.Success(FeedData(Feed(data.id, data.title, data.description, data.user, 0,data.category)))
    }

    fun mapToInsert(data: List<Long>) : Result<ArrayList<Long>> {
        return Result.Success(ArrayList(data))
    }


    fun mapToDelete(data: Int) : Result<Int> {
        return Result.Success(data)
    }
}