package com.example.domain.token

import com.example.domain.result.Result
import com.example.domain.SingleUseCase
import io.reactivex.Single

class VerifyTokenUserCase(private val tokenRepository: TokenRepository) : SingleUseCase<String, String>() {
    override fun execute(parameter: String): Single<Result<String>> {
        return tokenRepository.verifyToken(parameter)
    }
}