package com.example.model

import com.example.model.User

data class FollowerData(
    val list : ArrayList<User>,
    val isLast: Boolean,
    val next: Int
)