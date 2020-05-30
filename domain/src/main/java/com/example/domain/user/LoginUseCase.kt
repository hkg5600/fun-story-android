package com.example.domain.user

import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import com.example.model.LoginParameter
import com.example.model.token.Token
import io.reactivex.Single

class LoginUseCase(private val userRepository: UserRepository) : SingleUseCase<LoginParameter, Token>()  {
    override fun execute(parameter: LoginParameter): Single<Result<Token>> {
        return userRepository.login(parameter)
    }
}