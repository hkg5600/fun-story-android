package com.example.fun_story.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Event
import com.example.domain.token.TokenManager
import com.example.domain.user.GetMyInfoUseCase
import com.example.fun_story.BaseViewModel

class InfoViewModel(private val getMyInfoUseCase: GetMyInfoUseCase) : BaseViewModel() {


    private val _userName = MediatorLiveData<String>()
    val userName : LiveData<String>
        get() = _userName

    private val getMyInfoResult = getMyInfoUseCase.observe()

    init {
        getMyInfoResult.onSuccess(_userName) {
            _userName.value = it.data.user.username
        }

        getMyInfoResult.onError(_error) {
            when (it) {
                "network" ->_error.value =  Event("network")
                else -> _error.value = Event("알 수 없는 오류 발생")
            }
        }
    }

    fun initLoginState() {
        if (TokenManager.hasToken) {
            this(getMyInfoUseCase(Unit))
        } else {
            _userName.value = ""
        }
    }

    fun navigateToWrite() {

    }

    fun navigateToLogin() {
        if (TokenManager.hasToken) {

        } else {

        }
    }

    fun navigateToFollower() {

    }
}