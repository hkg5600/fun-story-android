package com.example.data.user

import com.example.domain.result.Result
import com.example.domain.token.TokenManager
import com.example.domain.user.UserRepository
import com.example.model.UserData
import com.example.model.FollowerData
import io.reactivex.Single

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepository {
    override fun getUserInfo(id: Int): Single<Result<UserData>> {
        return if (!TokenManager.hasToken) userRemoteDataSource.getUserInfo(id).map(UserMapper::map)
        else userRemoteDataSource.getUserInfoWithFollow(id).map(UserMapper::map)
    }

    override fun getMyInfo(): Single<Result<UserData>> {
        return userRemoteDataSource.getUserInfo().map(UserMapper::map)
    }

    override fun followUser(id: Int): Single<Result<String>> {
        return userRemoteDataSource.followUser(id).map(FollowMapper::map)
    }

    override fun getFollow(page: Int): Single<Result<FollowerData>> {
        return userRemoteDataSource.getFollower(page).map(GetFollowerMapper::map)
    }
}