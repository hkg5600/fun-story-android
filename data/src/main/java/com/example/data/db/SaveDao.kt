package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.FeedEntity
import io.reactivex.Single

@Dao
interface SaveDao {

    @Query("Select * From feedEntity Order by created_at")
    fun getAllFeed() : Single<List<FeedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FeedEntity) : Single<Long>

    @Query("Delete From feedEntity Where id = :id")
    fun delete(id: Int) : Single<Int>
}