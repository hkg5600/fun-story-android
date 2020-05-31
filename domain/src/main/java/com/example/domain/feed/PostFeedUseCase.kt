package com.example.domain.feed

import com.example.domain.SingleUseCase
import com.example.domain.result.Result
import com.example.model.PostFeedParameter
import io.reactivex.Single

class PostFeedUseCase(private val feedRepository: FeedRepository) : SingleUseCase<PostFeedParameter, String>() {
    override fun execute(parameter: PostFeedParameter): Single<Result<String>> {
        return feedRepository.postFeed(parameter)
    }
}