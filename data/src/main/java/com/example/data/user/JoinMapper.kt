package com.example.data.user

import com.example.data.NetworkDataMapper
import com.example.data.prefs.SharedPreferenceStorage
import com.example.domain.result.Result
import com.example.model.token.Token

class JoinMapper(private val sharedPreferenceStorage: SharedPreferenceStorage) :
    NetworkDataMapper<Token>() {
    override fun mapTo(data: Token): Result<Token> {
        sharedPreferenceStorage.setString("token", data.token)
        sharedPreferenceStorage.setString("refresh", data.refresh)
        return Result.Success(data)
    }
}