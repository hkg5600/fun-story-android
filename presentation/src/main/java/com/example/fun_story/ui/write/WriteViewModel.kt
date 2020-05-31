package com.example.fun_story.ui.write

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.feed.PostFeedUseCase
import com.example.domain.network.GetNetworkStateUseCase
import com.example.domain.result.Event
import com.example.domain.result.Result
import com.example.fun_story.BaseViewModel
import com.example.fun_story.CategorySelector
import com.example.model.FeedCategory
import com.example.model.PostFeedParameter

class WriteViewModel(private val getNetworkStateUseCase: GetNetworkStateUseCase, private val postFeedUseCase: PostFeedUseCase) : BaseViewModel(),
    CategorySelector {

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

    val feedTitle = ObservableField<String>()
    val feedDescription = ObservableField<String>()

    val feedCategoryList = FeedCategory.values().toCollection(ArrayList())

    private val _completePost = MediatorLiveData<Unit>()
    val completePost : LiveData<Unit>
        get() = _completePost

    private val _loading = MutableLiveData<Boolean>(false)
    val loading : LiveData<Boolean>
        get() = _loading

    init {
        _title.value = "카테고리 선택"
        _pageState.value = true
        _nextMenuVisible.value = true

        postFeedUseCase.observe().onSuccess(_completePost) {
            _loading.value = false
            if (it.data == "success to write")
                _completePost.value = Unit
        }

        postFeedUseCase.observe().onError(_error) {
            _loading.value = false
            _error.value = Event(it)
        }
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
            _title.value = "카테고리 선택"
        }
    }

    fun executePost() {
        if (!getNetworkState()) {
            return
        } else {
            _loading.value = true
            this(postFeedUseCase(createParameter()))
        }
    }

    fun setFinishEnable(enabled: Boolean) {
        _finishWriteEnable.value = enabled
    }

    override fun selectCategory(category: FeedCategory) {
        val isEqual = selectedCategory.value == category
        _nextMenuEnable.value = !isEqual
        _selectedCategory.value = if (isEqual) null else category
    }

    private fun getNetworkState(): Boolean {
        return when (val it = getNetworkStateUseCase(Unit)) {
            is Result.Success -> {
                if (!it.data)
                    _error.value = Event("network")
                it.data
            }
            is Result.Error -> {
                _error.value = Event("network")
                false
            }
        }
    }

    private fun createParameter(): PostFeedParameter {
        val title = feedTitle.get()!!
        val description = feedDescription.get()!!
        val category = selectedCategory.value!!
        return PostFeedParameter(title, description, category.id)
    }
}