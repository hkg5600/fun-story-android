package com.example.data.user

import com.example.data.NetworkDataMapper
import com.example.domain.result.Result
import com.example.model.UserData

object UserMapper: NetworkDataMapper<UserData>() {
    override fun mapTo(data: UserData): Result<UserData> {
        return Result.Success(data.apply {
            data.user.username.plus("작가님의 이야기")
        })
    }
}