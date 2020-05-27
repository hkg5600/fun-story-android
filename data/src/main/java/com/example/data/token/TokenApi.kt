package com.example.data.token

import com.example.model.Response
import com.example.model.token.TokenParameter
import com.example.model.token.Token
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    @POST("api/verify/")
    fun verifyToken(@Body tokenParameter: TokenParameter) : Single<retrofit2.Response<Response<String>>>

    @POST("api/refresh/")
    fun refreshToken(@Body tokenParameter: TokenParameter) : Single<retrofit2.Response<Response<Token>>>
}