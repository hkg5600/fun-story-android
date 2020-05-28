package com.example.data.user

import com.example.domain.result.Result
import com.example.domain.user.UserRepository
import com.example.model.UserData
import io.reactivex.Single

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource): UserRepository {
    override fun getUserInfo(id: Int): Single<Result<UserData>> {
        return userRemoteDataSource.getUserInfo(id).map(UserMapper::map)
    }
}