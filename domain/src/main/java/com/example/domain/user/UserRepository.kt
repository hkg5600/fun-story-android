package com.example.domain.user

import com.example.domain.result.Result
import com.example.model.UserData
import io.reactivex.Single

interface UserRepository {
    fun getUserInfo(id: Int): Single<Result<UserData>>
}