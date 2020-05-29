package com.example.domain.user

import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import io.reactivex.Single

class FollowUserUseCase(private val userRepository: UserRepository) : SingleUseCase<Int, String>() {
    override fun execute(parameter: Int): Single<Result<String>> {
        return userRepository.followUser(parameter)
    }
}