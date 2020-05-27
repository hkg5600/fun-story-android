package com.example.data.save

import com.example.data.feed.FeedApi
import com.example.model.FeedListData
import com.example.model.Response
import com.example.model.SaveListData
import io.reactivex.Single

class SaveRemoteDataSource(private val api: SaveApi) {

    fun getSaveList(): Single<retrofit2.Response<Response<SaveListData>>> {
        return api.getSaveList()
    }

    fun saveFeed(list: ArrayList<Int>): Single<retrofit2.Response<Response<String>>> {
        return api.saveFeed(list)
    }
}