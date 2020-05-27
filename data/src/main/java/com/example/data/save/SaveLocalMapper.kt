package com.example.data.save

import com.example.data.entity.FeedEntity
import com.example.domain.result.Result
import com.example.model.Feed
import com.example.model.SaveListData

object SaveLocalMapper {
    fun mapToData(data: List<FeedEntity>): Result<SaveListData> {
        val list = ArrayList(data.map {
            Feed(it.id, it.title, it.description, it.user, 0, it.category)
        }).apply { reverse() }
        val saveListData = SaveListData(list)
        return if (data.isNotEmpty()) Result.Success(saveListData) else {
            Result.Error("empty list")
        }
    }

    fun mapToInsert(data: Long): Result<Long> {
        return if (data.toInt() != -1) {
            Result.Success(data)
        } else
            Result.Error("server")
    }

    fun mapToDelete(data: Int) : Result<Int> {
        return if (data != -1) {
            Result.Success(data)
        } else
            Result.Error("server")
    }
}