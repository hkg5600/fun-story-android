package com.example.data.feed

import com.example.data.entity.FeedEntity
import com.example.model.Feed
import com.example.model.FeedData
import com.example.model.FeedListData
import com.example.domain.result.Result
import com.example.domain.feed.FeedRepository
import com.example.domain.feed.GetFeedParameter
import io.reactivex.Single

class FeedRepositoryImpl(
    private val feedRemoteDataSource: FeedRemoteDataSource,
    private val feedLocalDataSource: FeedLocalDataSource
) : FeedRepository {
    override fun getFeedList(getFeedParameter: GetFeedParameter): Single<Result<FeedListData>> {
        return getFeedParameter.run {
            if (isLocal)
                feedLocalDataSource.getFeedList().map(FeedLocalMapper::mapToData)
            else
                feedRemoteDataSource.getFeed(page, userId, isFollowing, category).map(FeedListDataRemoteMapper::map)

        }
    }

    override fun saveFeedList(feedList: ArrayList<Feed>): Single<Result<ArrayList<Long>>> {
        return feedLocalDataSource.deleteAll().flatMap {
            feedLocalDataSource.saveFeedList(getFeedEntity(feedList)).map(FeedLocalMapper::mapToInsert)
        }
    }

    override fun deleteAll(): Single<Result<Int>> {
        return feedLocalDataSource.deleteAll().map(FeedLocalMapper::mapToDelete)
    }

    override fun deleteFeed(id: Int): Single<Result<String>> {
        return feedRemoteDataSource.deleteFeed(id).map(FeedDeleteRemoteMapper::map)
    }

    override fun getFeedData(parameter: Int): Single<Result<FeedData>> {
        return feedLocalDataSource.getFeed(parameter).map(FeedLocalMapper::mapToData)
            .onErrorResumeNext(feedRemoteDataSource.getFeedDetail(parameter).map(FeedDataRemoteMapper::map))
    }

    private fun getFeedEntity(data: ArrayList<Feed>) : ArrayList<FeedEntity> {
        return with(data) {
            ArrayList(
                this.map {
                    FeedEntity(
                        it.id,
                        it.title,
                        it.description,
                        it.user,
                        it.category
                    )
                }
            )
        }
    }
}
