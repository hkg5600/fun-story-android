package com.example.data.token

import com.example.data.NetworkDataMapper
import com.example.model.token.Token
import com.example.domain.result.Result

object TokenDataMapper: NetworkDataMapper<Token>() {
    override fun mapTo(data: Token): Result<Token> {
        return Result.Success(data)
    }
}