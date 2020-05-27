package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.FeedEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(searchWord: ArrayList<FeedEntity>) : Single<List<Long>>

    @Query("Select * From feedEntity")
    fun getAllFeed() : Single<List<FeedEntity>>

    @Query("Select * From feedEntity Where id = :id")
    fun getFeed(id: Int) : Single<FeedEntity>

    @Query("Delete From feedEntity")
    fun deleteAll() : Single<Int>

}