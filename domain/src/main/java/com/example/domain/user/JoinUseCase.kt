package com.example.domain.user

import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import com.example.model.JoinParameter
import com.example.model.token.Token
import io.reactivex.Single

class JoinUseCase(private val userRepository: UserRepository) : SingleUseCase<JoinParameter, Token>() {
    override fun execute(parameter: JoinParameter): Single<Result<Token>> {
        return userRepository.join(parameter)
    }
}