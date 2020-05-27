package com.example.domain.token

import com.example.domain.UseCase
import com.example.domain.result.Result

class GetTokenUseCase(private val tokenRepository: TokenRepository) : UseCase<Unit, Pair<String, String>>(){
    override fun execute(parameter: Unit): Result<Pair<String, String>> {
        return tokenRepository.getToken()
    }

}