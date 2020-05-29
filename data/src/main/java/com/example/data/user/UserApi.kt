package com.example.data.user

import com.example.model.Response
import com.example.model.UserData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface UserApi {

    @GET
    fun getUserInfo(@Url url: String) : Single<retrofit2.Response<Response<UserData>>>

    @GET
    fun followUser(@Url url: String) : Single<retrofit2.Response<Response<String>>>
}