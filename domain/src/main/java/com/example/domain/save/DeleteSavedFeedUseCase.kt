package com.example.domain.save

import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import io.reactivex.Single

class DeleteSavedFeedUseCase(private val saveRepository: SaveRepository) : SingleUseCase<Int, Int>() {
    override fun execute(parameter: Int): Single<Result<Int>> {
        return saveRepository.deleteFeed(parameter)
    }
}