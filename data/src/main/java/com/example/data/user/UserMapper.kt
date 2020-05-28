package com.example.data.user

import com.example.data.NetworkDataMapper
import com.example.domain.result.Result
import com.example.model.UserData

object UserMapper: NetworkDataMapper<UserData>() {
    override fun mapTo(data: UserData): Result<UserData> {
        return Result.Success(data.apply {
            data.user.username = data.user.username + " 작가님의 글 목록"
        })
    }
}