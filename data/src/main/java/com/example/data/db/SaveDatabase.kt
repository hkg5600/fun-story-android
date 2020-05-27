package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entity.FeedEntity

@Database(entities = [(FeedEntity::class)], version = 2)
abstract class SaveDatabase : RoomDatabase() {
    abstract fun saveDao(): SaveDao
}