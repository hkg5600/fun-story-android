package com.example.fun_story.ui.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Event
import com.example.domain.save.DeleteSavedFeedUseCase
import com.example.domain.save.GetSaveFeedListUseCase
import com.example.domain.token.TokenManager
import com.example.fun_story.BaseViewModel
import com.example.fun_story.DetailNavigator
import com.example.model.Feed

class SaveViewModel(private val getSaveFeedListUseCase: GetSaveFeedListUseCase, private val deleteSavedFeedUseCase: DeleteSavedFeedUseCase) : BaseViewModel(),
    DetailNavigator {

    private val _startMessage = MutableLiveData<Event<String>>()
    val startMessage: LiveData<Event<String>>
        get() = _startMessage

    private val _deleteResult = MediatorLiveData<Event<Unit>>()
    val deleteResult: LiveData<Event<Unit>>
        get() = _deleteResult

    var allFeedList = ArrayList<Feed>()

    private val _feedList = MediatorLiveData<ArrayList<Feed>>()
    val feedList: LiveData<ArrayList<Feed>>
        get() = _feedList

    private val _navigateToDetail = MutableLiveData<Event<Int>>()
    val navigateToDetail : LiveData<Event<Int>>
        get() = _navigateToDetail

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing : LiveData<Boolean>
        get() = _isRefreshing

    private val deleteSavedFeedResult = deleteSavedFeedUseCase.observe()
    private val getSaveFeedResult = getSaveFeedListUseCase.observe()

    init {
        executeUseCase(true)
        _startMessage.value = Event("좌로 밀어 삭제")
        getSaveFeedResult.onSuccess(_feedList) {
            _isRefreshing.value = false
            if (it.data.list.size == 0)
                return@onSuccess
            _feedList.value = it.data.list
            allFeedList = it.data.list
        }

        getSaveFeedResult.onError(_error) {
            _isRefreshing.value = false
            when (it) {
                "empty list" -> {
                    if (TokenManager.hasToken)
                        executeUseCase(false)
                }
            }
        }

        deleteSavedFeedResult.onSuccess(_deleteResult) {
            _deleteResult.value = Event(Unit)
        }

        deleteSavedFeedResult.onError(_error) {
            _error.value = Event("삭제에 실패했습니다.")
        }
    }

    fun executeUseCase(isLocal: Boolean) {
        _isRefreshing.value = true
        this(getSaveFeedListUseCase(isLocal))
    }


    override fun navigateToDetail(id: Int) {
        _navigateToDetail.value = Event(id)
    }

    fun removeItem(id: Int) {
        val item = allFeedList[id]
        allFeedList.removeAt(id)
        this(deleteSavedFeedUseCase(item.id))
        _feedList.value = allFeedList
    }
}