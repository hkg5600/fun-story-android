package com.example.fun_story.ui.feed_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.feed.GetFeedDataUseCase
import com.example.domain.result.Event
import com.example.model.Feed
import com.example.fun_story.BaseViewModel

class FeedDetailViewModel(
    private val getFeedDataUseCase: GetFeedDataUseCase
) : BaseViewModel() {

    private var isHandled = false

    private val _userId = MutableLiveData<Int>()

    private val getFeedDetailResult = getFeedDataUseCase.observe()

    private val _feedDetail = MediatorLiveData<Feed>()
    val feedDetail: LiveData<Feed>
        get() = _feedDetail

    init {
        _feedDetail.addSource(_userId) {
            if (!isHandled) {
                this(getFeedDataUseCase(it))
                isHandled = true
            }
        }

        getFeedDetailResult.onSuccess(_feedDetail) {
            _feedDetail.value = it.data.story
        }

        getFeedDetailResult.onError(_error) {
            when (it) {
                "network", "no data","server" -> raiseNoData()
            }
        }


    }

    private fun raiseNoData() {
        _error.value = Event("이야기를 불러오는데 실패했습니다\n다시 시도해주세요")
    }

    fun setUerId(id: Int) {
        _userId.value = id
    }

    fun saveFeed() {
        //this(saveUserCase(feedDetail.value!!))
    }
}