package com.example.domain.feed_detail

import com.example.domain.SingleUseCase
import com.example.domain.feed.FeedRepository
import com.example.domain.result.Result
import io.reactivex.Single

class DeleteFeedUseCase(private val feedRepository: FeedRepository) : SingleUseCase<Int, String>() {
    override fun execute(parameter: Int): Single<Result<String>> {
        return feedRepository.deleteFeed(parameter)
    }
}