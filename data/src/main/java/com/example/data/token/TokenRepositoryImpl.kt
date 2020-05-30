package com.example.data.token

import com.example.data.prefs.SharedPreferenceStorage
import com.example.model.token.Token
import com.example.domain.result.Result
import com.example.domain.token.TokenRepository
import io.reactivex.Single
import java.lang.Exception

class TokenRepositoryImpl(private val tokenRemoteDataSource: TokenRemoteDataSource, private val sharedPreferenceStorage: SharedPreferenceStorage) :
    TokenRepository {
    override fun verifyToken(token: String): Single<Result<String>> {
        return tokenRemoteDataSource.verifyToken(token).map(TokenVerifyMapper::map)
    }

    override fun refreshToken(token: String): Single<Result<Token>> {
        return tokenRemoteDataSource.refreshToken(token).map(TokenDataMapper::map)
    }

    override fun getToken(): Result<Pair<String, String>>{
        val token = sharedPreferenceStorage.getString("token")
        val refresh = sharedPreferenceStorage.getString("refresh")
        return if (token.isNotBlank() && refresh.isNotBlank())
            Result.Success(Pair(token, refresh))
        else
            Result.Error("no token")
    }

    override fun removeToken(): Result<Unit> {
        return try {
            sharedPreferenceStorage.removeKey("token")
            sharedPreferenceStorage.removeKey("refresh")
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("삭제 실패")
        }

    }


}