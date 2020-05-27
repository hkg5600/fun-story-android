package com.example.model

data class Feed(
    val id: Int,
    val title: String,
    val description: String,
    val user: Int,
    val time: Int,
    val category: String
)