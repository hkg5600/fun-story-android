package com.example.domain.feed

import com.example.model.FeedListData
import com.example.domain.result.Result
import com.example.domain.SingleUseCase
import io.reactivex.Single

class GetFeedListUseCase(private val feedRepository: FeedRepository) : SingleUseCase<GetFeedParameter, FeedListData>() {

    override fun execute(parameter: GetFeedParameter): Single<Result<FeedListData>> {
        return feedRepository.getFeedList(parameter)
    }

}

data class GetFeedParameter(
    var page: Int,
    val userId: Int,
    var isFollowing: Int,
    var category: String,
    var isLocal: Boolean
)