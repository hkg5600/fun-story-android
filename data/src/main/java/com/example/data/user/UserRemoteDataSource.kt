package com.example.data.user

import com.example.model.Response
import com.example.model.UserData
import io.reactivex.Single

class UserRemoteDataSource(private val api:UserApi) {

    fun getUserInfo(id: Int): Single<retrofit2.Response<Response<UserData>>> {
        return api.getUserInfo("/api/user/$id/")
    }
}