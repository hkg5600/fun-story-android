package com.example.domain.feed

import com.example.model.FeedData
import com.example.domain.result.Result
import com.example.domain.SingleUseCase
import io.reactivex.Single

class GetFeedDataUseCase(private val feedRepository: FeedRepository) : SingleUseCase<Int, FeedData>() {
    override fun execute(parameter: Int): Single<Result<FeedData>> {
        return feedRepository.getFeedDetail(parameter)
    }
}