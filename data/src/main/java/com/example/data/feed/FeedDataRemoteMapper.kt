package com.example.data.feed

import com.example.data.NetworkDataMapper
import com.example.domain.result.Result
import com.example.model.FeedData

object FeedDataRemoteMapper: NetworkDataMapper<FeedData>() {
    override fun mapTo(data: FeedData): Result<FeedData> {
        return Result.Success(data)
    }
}