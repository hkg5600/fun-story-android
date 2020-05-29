package com.example.domain.user

import com.example.domain.result.Result
import com.example.model.UserData
import com.example.model.FollowerData
import io.reactivex.Single

interface UserRepository {
    fun getUserInfo(id: Int): Single<Result<UserData>>
    fun getMyInfo() : Single<Result<UserData>>
    fun followUser(id: Int): Single<Result<String>>
    fun getFollow(page: Int) : Single<Result<FollowerData>>
}