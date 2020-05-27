package com.example.domain

import android.annotation.SuppressLint
import com.example.domain.result.Result

abstract class UseCase<T, R>() {

    @SuppressLint("CheckResult")
    operator fun invoke(parameter: T): Result<R> {
        return execute(parameter)
    }

    abstract fun execute(parameter: T): Result<R>
}
