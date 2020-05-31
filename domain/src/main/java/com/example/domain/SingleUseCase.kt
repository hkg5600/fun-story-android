package com.example.domain

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
import com.example.domain.result.Result
import com.example.domain.token.TokenManager
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException

abstract class SingleUseCase<T, R>() {
    private val result = MutableLiveData<Result<R>>()
    fun observe() = result

    @SuppressLint("CheckResult")
    operator fun invoke(parameter: T): Disposable {
        return execute(parameter).subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen(TokenManager.getRetry())
            .subscribe({
                result.value = it
            }, {
                Log.e("UseCase Error", it.toString())
                when (it) {
                    is SocketTimeoutException, is SocketException, is ConnectException -> result.value =
                        Result.Error("network")
                    is EmptyResultSetException -> result.value = Result.Error("no such table")
                    else -> result.value = Result.Error("token")
                }
            })
    }

    abstract fun execute(parameter: T): Single<Result<R>>
}
