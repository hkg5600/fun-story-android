package com.example.data.feed

import com.example.data.NetworkDataMapper
import com.example.domain.result.Result
import com.example.model.FeedData
import com.example.model.FeedListData

object FeedListDataRemoteMapper : NetworkDataMapper<FeedListData>()  {
    override fun mapTo(data: FeedListData): Result<FeedListData> {
        return Result.Success(data)
    }
}