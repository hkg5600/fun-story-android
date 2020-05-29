package com.example.fun_story.ui.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Event
import com.example.domain.user.GetFollowerUesCase
import com.example.fun_story.BaseViewModel
import com.example.model.FollowerData
import com.example.model.User

class FollowerViewModel(private val getFollowerUesCase: GetFollowerUesCase) : BaseViewModel() {
    private val _navigateToUser = MutableLiveData<Event<Int>>()
    val navigateToUser : LiveData<Event<Int>>
        get() = _navigateToUser

    private val allFollowerList = ArrayList<User>()

    private val _followerList = MediatorLiveData<FollowerData>()
    val followerList : LiveData<FollowerData>
        get() = _followerList

    init {
        this(getFollowerUesCase(0))

        getFollowerUesCase.observe().onSuccess(_followerList) {
            val data = it.data
            allFollowerList.addAll(data.list)
            data.list.clear()
            data.list.addAll(allFollowerList)
            _followerList.value = data
        }

        getFollowerUesCase.observe().onError(_error) {
            when (it) {
                "network" -> _error.value = Event("네트워크 연결을 확인해주세요")
                else -> _error.value = Event(it)
            }
        }
    }

    fun executeGetFollower(page: Int) {
        if (page==0)
            allFollowerList.clear()
        this(getFollowerUesCase(page))
    }

    fun navigateToUser(id: Int) {
        _navigateToUser.value = Event(id)
    }
}