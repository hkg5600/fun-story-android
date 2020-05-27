package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.entity.FeedEntity
import io.reactivex.Single

@Dao
interface SaveDao {

    @Query("Select * From feedEntity")
    fun getAllFeed() : Single<List<FeedEntity>>

    @Insert
    fun insert(entity: FeedEntity) : Single<Long>
}