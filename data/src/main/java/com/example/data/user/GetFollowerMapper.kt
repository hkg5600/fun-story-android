package com.example.data.user

import com.example.data.NetworkDataMapper
import com.example.domain.result.Result
import com.example.model.FollowerData

object GetFollowerMapper : NetworkDataMapper<FollowerData>() {
    override fun mapTo(data: FollowerData): Result<FollowerData> {
        return Result.Success(data)
    }
}