package com.example.data.save

import com.example.model.FeedListData
import com.example.model.Response
import com.example.model.SaveListData
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface SaveApi {

    @GET("/api/my-story/")
    fun getSaveList(): Single<retrofit2.Response<Response<SaveListData>>>

    @POST("/api/save-story/")
    fun saveFeed(@Body parameter: ArrayList<Int>) : Single<retrofit2.Response<Response<String>>>
}