package com.example.domain.user

import com.example.domain.result.Result
import com.example.model.UserData
import com.example.model.FollowerData
import com.example.model.JoinParameter
import com.example.model.LoginParameter
import com.example.model.token.Token
import io.reactivex.Single

interface UserRepository {
    fun getUserInfo(id: Int): Single<Result<UserData>>
    fun getMyInfo() : Single<Result<UserData>>
    fun followUser(id: Int): Single<Result<String>>
    fun getFollow(page: Int) : Single<Result<FollowerData>>
    fun login(loginParameter: LoginParameter) : Single<Result<Token>>
    fun join(joinParameter: JoinParameter) : Single<Result<Token>>
}