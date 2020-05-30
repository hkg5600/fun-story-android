package com.example.domain.token

import com.example.domain.UseCase
import com.example.domain.result.Result

class RemoveTokenUseCase(private val tokenRepository: TokenRepository) : UseCase<Unit, Unit>() {
    override fun execute(parameter: Unit): Result<Unit> {
        return tokenRepository.removeToken()
    }
}