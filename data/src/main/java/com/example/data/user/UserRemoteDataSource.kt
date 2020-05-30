package com.example.data.user

import com.example.model.*
import com.example.model.token.Token
import io.reactivex.Single

class UserRemoteDataSource(private val api:UserApi) {

    fun getUserInfo(id: Int): Single<retrofit2.Response<Response<UserData>>> {
        return api.getUserInfo("/api/user/$id/")
    }

    fun getUserInfoWithFollow(id: Int) : Single<retrofit2.Response<Response<UserData>>> {
        return api.getUserInfo("/api/user-info/$id/")
    }

    fun getUserInfo(): Single<retrofit2.Response<Response<UserData>>> {
        return api.getUserInfo("/api/my-info/")
    }

    fun followUser(id: Int) : Single<retrofit2.Response<Response<String>>> {
        return api.followUser("/api/follow/$id/")
    }

    fun getFollower(page: Int) : Single<retrofit2.Response<Response<FollowerData>>> {
        return api.getFollower("/api/my-follow/$page/")
    }

    fun login(loginParameter: LoginParameter) : Single<retrofit2.Response<Response<Token>>> {
        return api.login(loginParameter)
    }

    fun join(joinParameter: JoinParameter) : Single<retrofit2.Response<Response<Token>>> {
        return api.join(joinParameter)
    }
}