package com.example.data.token

import com.example.model.Response
import com.example.model.token.TokenParameter
import com.example.model.token.Token
import io.reactivex.Single

class TokenRemoteDataSource(private val api: TokenApi) {

    fun verifyToken(token: String): Single<retrofit2.Response<Response<String>>> {
        return api.verifyToken(TokenParameter((token)))
    }

    fun refreshToken(token: String): Single<retrofit2.Response<Response<Token>>> {
        return api.refreshToken(TokenParameter(token))
    }
}