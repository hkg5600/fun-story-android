package com.example.model

enum class FeedCategory(val categoryName: String, val id: String) {
    Fun("유머 / 개그", "100"),
    Horror("공포 / 스릴러", "101"),
    Sad("감동 / 슬픈", "103"),
    Knowledge("상식 / 지식", "102"),
    Romance("연애", "104"),
    Poem("시", "105")
}