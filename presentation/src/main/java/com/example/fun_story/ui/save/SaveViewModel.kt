package com.example.fun_story.ui.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Event
import com.example.domain.save.GetSaveFeedListUseCase
import com.example.domain.token.TokenManager
import com.example.fun_story.BaseViewModel
import com.example.fun_story.DetailNavigator
import com.example.model.Feed

class SaveViewModel(private val getSaveFeedListUseCase: GetSaveFeedListUseCase) : BaseViewModel(),
    DetailNavigator {


    private val _feedList = MediatorLiveData<ArrayList<Feed>>()
    val feedList: LiveData<ArrayList<Feed>>
        get() = _feedList

    private val _navigateToDetail = MutableLiveData<Event<Int>>()
    val navigateToDetail : LiveData<Event<Int>>
        get() = _navigateToDetail

    private val getSaveFeedResult = getSaveFeedListUseCase.observe()

    init {
        executeUseCase(true)

        getSaveFeedResult.onSuccess(_feedList) {
            if (it.data.list.size == 0)
                return@onSuccess
            _feedList.value = it.data.list
        }

        getSaveFeedResult.onError(_error) {
            when (it) {
                "empty list" -> {
                    if (TokenManager.hasToken)
                        executeUseCase(false)
                }
            }
        }
    }

    fun executeUseCase(isLocal: Boolean) {
        this(getSaveFeedListUseCase(isLocal))
    }


    override fun navigateToDetail(id: Int) {
        _navigateToDetail.value = Event(id)
    }
}