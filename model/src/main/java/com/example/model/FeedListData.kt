package com.example.model

import com.example.model.Feed

data class FeedListData(
    val list : ArrayList<Feed>,
    val isLast: Boolean,
    val next: Int
)