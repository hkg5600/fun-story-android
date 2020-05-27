package com.example.domain.feed

import com.example.domain.result.Result
import com.example.domain.SingleUseCase
import com.example.model.Feed
import io.reactivex.Single

class SaveFeedListUseCase(private val feedRepository: FeedRepository) : SingleUseCase<ArrayList<Feed>, ArrayList<Long>>() {
    override fun execute(parameter: ArrayList<Feed>): Single<Result<ArrayList<Long>>> {
        return feedRepository.saveFeedList(parameter)
    }
}