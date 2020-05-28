package com.example.fun_story.ui.feed_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.feed_detail.GetFeedDataUseCase
import com.example.domain.feed_detail.SaveFeedUseCase
import com.example.domain.network.GetNetworkStateUseCase
import com.example.domain.result.Event
import com.example.domain.result.Result
import com.example.model.Feed
import com.example.fun_story.BaseViewModel

class FeedDetailViewModel(
    private val getFeedDataUseCase: GetFeedDataUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val saveFeedUseCase: SaveFeedUseCase
) : BaseViewModel() {

    private var isHandled = false

    private val _userId = MutableLiveData<Int>()

    private val getFeedDetailResult = getFeedDataUseCase.observe()

    private val _feedData = MediatorLiveData<Feed>()
    val feedData: LiveData<Feed>
        get() = _feedData

    private val _saveResult = MediatorLiveData<Event<Boolean>>()
    val saveResult : LiveData<Event<Boolean>>
        get() = _saveResult

    init {
        _feedData.addSource(_userId) {
            if (!isHandled) {
                this(getFeedDataUseCase(it))
                isHandled = true
            }
        }

        getFeedDetailResult.onSuccess(_feedData) {
            _feedData.value = it.data.story
        }

        getFeedDetailResult.onError(_error) {
            when (it) {
                "network", "no data","server" -> raiseNoData()
            }
        }

        saveFeedUseCase.observe().onSuccess(_saveResult) {
            _saveResult.value = Event(true)
        }

        saveFeedUseCase.observe().onError(_error) {
            _saveResult.value = Event(false)
        }


    }

    private fun raiseNoData() {
        _error.value = Event("이야기를 불러오는데 실패했습니다\n다시 시도해주세요")
    }

    fun setUserId(id: Int) {
        _userId.value = id
    }

    fun saveFeed() {
        feedData.value?.let{
            this(saveFeedUseCase(it))
        } ?: run {
            _error.value = Event("저장에 실패했습니다.")
        }
    }

    fun raiseNetworkError() {
        _error.value = Event("network")
    }

    fun getNetworkState(): Boolean {
        return when (val it = getNetworkStateUseCase(Unit)) {
            is Result.Success -> {
               it.data
            }
            is Result.Error -> {
                false
            }
        }
    }
}