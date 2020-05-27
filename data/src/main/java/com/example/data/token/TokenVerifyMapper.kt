package com.example.data.token

import com.example.data.NetworkDataMapper
import com.example.data.NetworkMessageMapper
import com.example.domain.result.Result

object TokenVerifyMapper : NetworkMessageMapper<String>() {
    override fun mapTo(data: String): Result<String> {
        return Result.Success(data)
    }
}