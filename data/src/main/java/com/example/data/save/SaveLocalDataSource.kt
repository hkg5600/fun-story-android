package com.example.data.save

import com.example.data.db.SaveDatabase
import com.example.data.entity.FeedEntity
import io.reactivex.Single

class SaveLocalDataSource(private val saveDatabase: SaveDatabase) {

    private val saveDao = saveDatabase.saveDao()

    fun getSavedFeedList(): Single<List<FeedEntity>> {
        return saveDao.getAllFeed()
    }

    fun saveFeed(entity: FeedEntity) : Single<Long> {
        return saveDao.insert(entity)
    }
}