package com.example.domain.user

import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import com.example.model.FollowerData
import io.reactivex.Single

class GetFollowerUesCase(private val userRepository: UserRepository): SingleUseCase<Int, FollowerData>() {
    override fun execute(parameter: Int): Single<Result<FollowerData>> {
        return userRepository.getFollow(parameter)
    }
}