package com.example.fun_story.ui.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.feed.GetFeedListUseCase
import com.example.domain.feed.GetFeedParameter
import com.example.domain.feed_detail.DeleteFeedUseCase
import com.example.domain.network.GetNetworkStateUseCase
import com.example.domain.result.Event
import com.example.domain.result.Result
import com.example.domain.token.TokenManager
import com.example.domain.user.FollowUserUseCase
import com.example.domain.user.GetUserInfoUseCase
import com.example.fun_story.BaseViewModel
import com.example.fun_story.DetailNavigator
import com.example.model.Feed
import com.example.model.FeedListData
import com.example.model.UserData

class FollowerViewModel(
    private val getFeedListUseCase: GetFeedListUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase,
    private val followUserUseCase: FollowUserUseCase
) : BaseViewModel(), DetailNavigator {

    private val _followingState = MutableLiveData(false)
    val followingState: LiveData<Boolean>
        get() = _followingState

    private var isHandled = false

    private val allFeedList = ArrayList<Feed>()

    private val getFeedParameter = GetFeedParameter(0, 0, 0, "0", false)

    private val _userInfo = MediatorLiveData<UserData>()
    val userInfo: LiveData<UserData>
        get() = _userInfo

    private val _feedList = MediatorLiveData<FeedListData>()
    val feedList: LiveData<FeedListData>
        get() = _feedList

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _userId = MutableLiveData<Int>()

    private val getFeedResult = getFeedListUseCase.observe()

    private val _deleteResult = MediatorLiveData<Event<Boolean>>()
    val deleteResult: LiveData<Event<Boolean>>
        get() = _deleteResult

    private val _followResult = MediatorLiveData<String>()
    val followResult: LiveData<String>
        get() = _followResult

    private val _navigateToDetail = MutableLiveData<Event<Int>>()
    val navigateToDetail: LiveData<Event<Int>>
        get() = _navigateToDetail

    init {
        _feedList.addSource(_userId) {
            if (!isHandled) {
                getFeedParameter.userId = it
                this(getUserInfoUseCase(it))
                executeGetFeed(0)
                isHandled = true
            }
        }

        getUserInfoUseCase.observe().onSuccess(_userInfo) {
            _userInfo.value = it.data
            _followingState.value = it.data.follow
        }

        getUserInfoUseCase.observe().onError(_error) {
            _error.value = Event(it)
        }

        getFeedResult.onSuccess(_feedList) {
            _isRefreshing.value = false
            val feedList = it.data
            allFeedList.addAll(feedList.list)
            feedList.list.clear()
            feedList.list.addAll(allFeedList)
            _feedList.value = feedList
        }

        getFeedResult.onError(_error) {
            _isRefreshing.value = false
            when (it) {
                "network" -> _error.value = Event("network")
            }
        }

        deleteFeedUseCase.observe().onSuccess(_feedList) {
            executeGetFeed(0)
        }

        deleteFeedUseCase.observe().onError(_error) {
            when (it) {
                "network" -> _error.value = Event("network")
                else -> _error.value = Event("삭제 실패")
            }
        }

        followUserUseCase.observe().onSuccess(_followResult) {
            _followResult.value = if (it.data == "success to follow") {
                _followingState.value = true
                "구독하였습니다"
            } else {
                _followingState.value = false
                "구독을 취소하였습니다"
            }
        }

        followUserUseCase.observe().onError(_error) {
            _error.value = Event(it)
        }

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

    fun follow() {
        if (!TokenManager.hasToken) {
            _error.value = Event("need token")
            return
        }
        this(followUserUseCase(userInfo.value?.user?.id!!))
    }

    fun executeGetFeed(page: Int) {
        if (!getNetworkState()) {
            _isRefreshing.value = false
            return
        }
        _isRefreshing.value = true
        if (page == 0)
            allFeedList.clear()
        getFeedParameter.page = page
        this(getFeedListUseCase(getFeedParameter))
    }

    override fun navigateToDetail(id: Int) {
        _navigateToDetail.value = Event(id)
    }

    fun setUserId(id: Int) {
        _userId.value = id
    }

    fun removeItem(id: Int) {
        val item = allFeedList[id]
        allFeedList.removeAt(id)
        deleteFeedUseCase(item.id)
    }

}