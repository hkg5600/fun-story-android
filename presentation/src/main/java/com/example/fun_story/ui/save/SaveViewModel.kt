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

    private val _textViewVisibility = MutableLiveData<Boolean>(false)
    val textViewVisibility : LiveData<Boolean>
        get() = _textViewVisibility

    private val deleteSavedFeedResult = deleteSavedFeedUseCase.observe()
    private val getSaveFeedResult = getSaveFeedListUseCase.observe()

    init {
        executeUseCase()
        _startMessage.value = Event("보관 항목은 기기에 저장됩니다.\n앱 삭제시 보관 항목은 삭제됩니다.")
        getSaveFeedResult.onSuccess(_feedList) {
            _isRefreshing.value = false
            _textViewVisibility.value = false
            _feedList.value = it.data.list
            allFeedList = it.data.list
        }

        getSaveFeedResult.onError(_error) {
            _isRefreshing.value = false
            when (it) {
                "empty list" -> {
                    _textViewVisibility.value = true
                }
            }
        }

        deleteSavedFeedResult.onSuccess(_deleteResult) {
            _deleteResult.value = Event(Unit)
            if (allFeedList.isEmpty())
                _textViewVisibility.value = true
        }

        deleteSavedFeedResult.onError(_error) {
            _error.value = Event("삭제에 실패했습니다.")
        }
    }

    fun executeUseCase() {
        _isRefreshing.value = true
        this(getSaveFeedListUseCase(Unit))
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