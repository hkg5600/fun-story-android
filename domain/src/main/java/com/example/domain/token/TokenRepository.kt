package com.example.domain.token

import com.example.model.token.Token
import com.example.domain.result.Result
import io.reactivex.Single

interface TokenRepository {
    fun verifyToken(token: String) : Single<Result<String>>
    fun refreshToken(token: String) : Single<Result<Token>>
    fun getToken() : Result<Pair<String, String>>
}