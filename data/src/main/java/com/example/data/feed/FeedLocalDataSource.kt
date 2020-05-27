package com.example.data.feed

import com.example.data.db.FeedDatabase
import com.example.data.entity.FeedEntity
import io.reactivex.Flowable
import io.reactivex.Single

class FeedLocalDataSource(private val feedDatabase: FeedDatabase) {
    private val feedDao = feedDatabase.feedDao()

    fun saveFeedList(feedList : ArrayList<FeedEntity>): Single<List<Long>> {
        return feedDao.insertAll(feedList)
    }

    fun getFeedList() : Single<List<FeedEntity>> {
        return feedDao.getAllFeed()
    }

    fun getFeed(id: Int) : Single<FeedEntity> {
        return feedDao.getFeed(id)
    }

    fun deleteAll() : Single<Int> {
        return feedDao.deleteAll()
    }
}