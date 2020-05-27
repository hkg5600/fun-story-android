package com.example.data

import com.example.domain.result.Result
import com.example.model.Response
import retrofit2.HttpException

abstract class NetworkMessageMapper<R> {


    fun map(data: retrofit2.Response<Response<R>>): Result<String> {
        return if (data.isSuccessful) {
            if (data.body()?.status == 200)
                mapTo(data.body()?.message!!)
            else
                Result.Error(data.body()?.message!!)
        } else {
            if (data.code() == 401)
                throw HttpException(data)
            else
                Result.Error("server")
        }
    }

    abstract fun mapTo(data: String): Result<String>
}