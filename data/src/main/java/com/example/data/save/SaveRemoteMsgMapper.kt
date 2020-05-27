package com.example.data.save

import com.example.data.NetworkMessageMapper
import com.example.domain.result.Result
import com.example.model.SaveListData

object SaveRemoteMsgMapper : NetworkMessageMapper<String>() {
    override fun mapTo(data: String): Result<String> {
        return Result.Success(data)
    }
}