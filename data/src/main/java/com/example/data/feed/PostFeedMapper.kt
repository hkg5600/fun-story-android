package com.example.data.feed

import com.example.data.NetworkMessageMapper
import com.example.domain.result.Result

object PostFeedMapper : NetworkMessageMapper<String>() {
    override fun mapTo(data: String): Result<String> {
        return Result.Success(data)
    }
}