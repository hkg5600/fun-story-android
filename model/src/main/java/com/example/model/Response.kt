package com.example.model

data class Response<T>(
    val status: Int,
    val data: T,
    val message: String
)