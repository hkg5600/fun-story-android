package com.example.domain.user


import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import com.example.model.UserData
import io.reactivex.Single

class GetMyInfoUseCase(private val userRepository: UserRepository) : SingleUseCase<Unit, UserData>() {
    override fun execute(parameter: Unit): Single<Result<UserData>> {
        return userRepository.getMyInfo()
    }
}