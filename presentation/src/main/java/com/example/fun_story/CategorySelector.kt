package com.example.fun_story

import com.example.model.FeedCategory

interface CategorySelector {
    fun selectCategory(category: FeedCategory)
}