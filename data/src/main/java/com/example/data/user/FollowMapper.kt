package com.example.data.user

import com.example.data.NetworkMessageMapper
import com.example.domain.result.Result

object FollowMapper : NetworkMessageMapper<String>() {
    override fun mapTo(data: String): Result<String> {
        return Result.Success(data)
    }
}