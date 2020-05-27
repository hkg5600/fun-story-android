package com.example.domain.token

import com.example.model.token.Token
import com.example.domain.result.Result
import com.example.domain.SingleUseCase
import io.reactivex.Single

class RefreshTokenUseCase(private val tokenRepository: TokenRepository) : SingleUseCase<String, Token>() {
    override fun execute(parameter: String): Single<Result<Token>> {
        return tokenRepository.refreshToken(parameter)
    }
}