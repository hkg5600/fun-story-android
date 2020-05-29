package com.example.data.user

import com.example.model.Response
import com.example.model.UserData
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
}