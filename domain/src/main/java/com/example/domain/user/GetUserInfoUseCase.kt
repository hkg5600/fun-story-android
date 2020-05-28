package com.example.domain.user

import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import com.example.model.UserData
import io.reactivex.Single

class GetUserInfoUseCase(private val userRepository: UserRepository) : SingleUseCase<Int, UserData>() {
    override fun execute(parameter: Int): Single<Result<UserData>> {
        return userRepository.getUserInfo(parameter)
    }

}