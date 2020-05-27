package com.example.data.save

import com.example.data.entity.FeedEntity
import com.example.domain.result.Result
import com.example.domain.save.SaveRepository
import com.example.domain.token.TokenManager
import com.example.model.Feed
import com.example.model.SaveListData
import io.reactivex.Single

class SaveRepositoryImpl(
    private val saveLocalDataSource: SaveLocalDataSource,
    private val saveRemoteDataSource: SaveRemoteDataSource
) : SaveRepository {
    override fun getSavedFeedList(): Single<Result<SaveListData>> {
        return saveLocalDataSource.getSavedFeedList().map(SaveLocalMapper::mapToData)
            .flatMap {
                when (it) {
                    is Result.Success -> Single.just(it)
                    is Result.Error -> saveRemoteDataSource.getSaveList().map(SaveRemoteMapper::map)
                }
            }
    }

    override fun saveFeed(entity: Feed): Single<Result<Long>> {
        return saveLocalDataSource.saveFeed(
            FeedEntity(
                entity.id,
                entity.title,
                entity.description,
                entity.user,
                entity.category
            )
        ).map(SaveLocalMapper::mapToInsert)
    }

    override fun saveFeedList(feedList: ArrayList<Int>): Single<Result<String>> {
        return saveRemoteDataSource.saveFeed(feedList).map(SaveRemoteMsgMapper::map)
    }
}