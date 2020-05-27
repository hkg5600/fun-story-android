package com.example.data.save

import com.example.data.NetworkDataMapper
import com.example.domain.result.Result
import com.example.model.SaveListData

object SaveRemoteMapper : NetworkDataMapper<SaveListData>() {
    override fun mapTo(data: SaveListData): Result<SaveListData> {
        return Result.Success(data)
    }
}