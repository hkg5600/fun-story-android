package com.example.fun_story.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Result
import com.example.domain.feed.GetFeedListUseCase
import com.example.domain.feed.GetFeedParameter
import com.example.domain.feed.SaveFeedListUseCase
import com.example.domain.network.GetNetworkStateUseCase
import com.example.domain.result.Event
import com.example.domain.token.TokenManager
import com.example.fun_story.BaseViewModel
import com.example.fun_story.DetailNavigator
import com.example.model.Feed
import com.example.model.FeedListData

class FeedViewModel(
    private val getFeedListUseCase: GetFeedListUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val saveFeedListUseCase: SaveFeedListUseCase
) : BaseViewModel(), DetailNavigator {

    private val _startMessage = MutableLiveData<Event<String>>()
    val startMessage: LiveData<Event<String>>
        get() = _startMessage

    private val getFeedParameter = GetFeedParameter(0, 0, 0, "0", true)

    private val allFeedList = ArrayList<Feed>()

    private val getFeedResult = getFeedListUseCase.observe()

    private val _feedList = MediatorLiveData<FeedListData>()
    val feedList: LiveData<FeedListData>
        get() = _feedList

    private val _motionLayoutState = MutableLiveData<Boolean>(true)
    val motionLayoutState: LiveData<Boolean>
        get() = _motionLayoutState

    private val _selectedCategory = MutableLiveData<FeedCategory?>()
    val selectedCategory: LiveData<FeedCategory?>
        get() = _selectedCategory

    private val _navigateToDetail = MutableLiveData<Event<Int>>()
    val navigateToDetail: LiveData<Event<Int>>
        get() = _navigateToDetail

    val feedCategoryList = FeedCategory.values().toCollection(ArrayList())

    init {
        _startMessage.value = Event("스와이프하여 피드를 새로고침")
        getFeedResult.onSuccess(_feedList) {
            val feedList = it.data
            allFeedList.addAll(feedList.list)
            feedList.list.clear()
            feedList.list.addAll(allFeedList)
            _feedList.value = feedList

            when (feedList.next) {
                0 -> executeGetFeed(0)
                1 -> this(saveFeedListUseCase(it.data.list))
            }
        }

        getFeedResult.onError(_error) {
            _error.value = Event(it)
        }

        this(getFeedListUseCase(getFeedParameter))
    }

    private fun getNetworkState(): Boolean {
        return when (val it = getNetworkStateUseCase(Unit)) {
            is Result.Success -> {
                if (it.data)
                    getFeedParameter.isLocal = false
                else {
                    _error.value = Event("network")
                    getFeedParameter.isLocal = true
                }
                it.data
            }
            is Result.Error -> {
                getFeedParameter.isLocal = true
                _error.value = Event("network")
                false
            }
        }
    }

    fun executeGetFeed(page: Int) {
        if (!getNetworkState()) return
        if (page == 0) allFeedList.clear()
        getFeedParameter.page = page
        this(getFeedListUseCase(getFeedParameter))
    }

    fun changeMotionLayoutState(it: Boolean) {
        _motionLayoutState.value = it
    }

    fun selectCategory(category: FeedCategory) {
        val isEqual = selectedCategory.value == category
        _selectedCategory.value = if (isEqual) null else category
        getFeedParameter.category = if (isEqual) "0" else category.id
        executeGetFeed(0)
    }

    fun toggleSwitch(isChecked: Boolean) {
        if (!TokenManager.hasToken) {
            _error.value = Event("로그인이 필요한 기능입니다")
            return
        }
        getFeedParameter.isFollowing = if (isChecked) 1 else 0
        executeGetFeed(0)
    }

    override fun navigateToDetail(id: Int) {
        _navigateToDetail.value = Event(id)
    }
}

enum class FeedCategory(val categoryName: String, val id: String) {
    Fun("유머 / 개그", "100"),
    Horror("공포 / 스릴러", "101"),
    Sad("감동 / 슬픈", "103"),
    Knowledge("상식 / 지식", "102"),
    Romance("연애", "104"),
    Poem("시", "105")
}