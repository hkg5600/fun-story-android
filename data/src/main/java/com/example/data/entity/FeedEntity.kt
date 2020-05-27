package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.Feed

@Entity
data class FeedEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name="title")
    val title: String,
    @ColumnInfo(name="description")
    val description: String,
    @ColumnInfo(name="user")
    val user: Int,
    @ColumnInfo(name="category")
    val category: String
)