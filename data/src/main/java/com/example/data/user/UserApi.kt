package com.example.data.user

import com.example.model.*
import com.example.model.token.Token
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface UserApi {

    @GET
    fun getUserInfo(@Url url: String) : Single<retrofit2.Response<Response<UserData>>>

    @GET
    fun followUser(@Url url: String) : Single<retrofit2.Response<Response<String>>>

    @GET
    fun getFollower(@Url url: String) : Single<retrofit2.Response<Response<FollowerData>>>

    @POST("/api/login/")
    fun login(@Body loginParameter: LoginParameter) : Single<retrofit2.Response<Response<Token>>>

    @POST("/api/join/")
    fun join(@Body joinParameter: JoinParameter): Single<retrofit2.Response<Response<Token>>>
}