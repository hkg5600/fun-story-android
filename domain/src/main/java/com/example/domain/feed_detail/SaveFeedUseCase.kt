package com.example.domain.feed_detail

import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import com.example.domain.save.SaveRepository
import com.example.model.Feed
import io.reactivex.Single

class SaveFeedUseCase(private val saveRepository: SaveRepository) : SingleUseCase<Feed, Long>() {
    override fun execute(parameter: Feed): Single<Result<Long>> {
        return saveRepository.saveFeed(parameter)
    }
}