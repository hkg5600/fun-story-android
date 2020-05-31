package com.example.fun_story.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fun_story.BaseViewModel
import com.example.fun_story.CategorySelector
import com.example.model.FeedCategory

class WriteViewModel : BaseViewModel(), CategorySelector {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _nextMenuVisible = MutableLiveData(false)
    val nextMenuVisible: LiveData<Boolean>
        get() = _nextMenuVisible

    private val _nextMenuEnable = MutableLiveData(false)
    val nextMenuEnable: LiveData<Boolean>
        get() = _nextMenuEnable

    private val _finishWriteVisible = MutableLiveData(false)
    val finishWriteVisible: LiveData<Boolean>
        get() = _finishWriteVisible


    private val _finishWriteEnable = MutableLiveData(false)
    val finishWriteEnable: LiveData<Boolean>
        get() = _finishWriteEnable

    private val _selectedCategory = MutableLiveData<FeedCategory?>()
    val selectedCategory: LiveData<FeedCategory?>
        get() = _selectedCategory

    private val _pageState = MutableLiveData<Boolean>()
    val pageState: LiveData<Boolean>
        get() = _pageState

    val feedCategoryList = FeedCategory.values().toCollection(ArrayList())

    init {
        _title.value = "카테고리 선택"
        _pageState.value = true
        _nextMenuVisible.value = true
    }

    fun setPage(isNext: Boolean) {
        if (isNext) {
            _nextMenuVisible.value = false
            _nextMenuEnable.value = false
            _finishWriteEnable.value = false
            _finishWriteVisible.value = true
            _pageState.value = false
            _title.value = "이야기 작성"
        } else {
            _nextMenuVisible.value = true
            _nextMenuEnable.value = true
            _finishWriteEnable.value = false
            _finishWriteVisible.value = false
            _pageState.value = true
        }
    }


    override fun selectCategory(category: FeedCategory) {
        val isEqual = selectedCategory.value == category
        _nextMenuEnable.value = !isEqual
        _selectedCategory.value = if (isEqual) null else category
    }
}