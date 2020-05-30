package com.example.fun_story.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Event
import com.example.domain.result.Result
import com.example.domain.token.RemoveTokenUseCase
import com.example.domain.token.TokenManager
import com.example.domain.user.GetMyInfoUseCase
import com.example.fun_story.BaseViewModel

class InfoViewModel(private val getMyInfoUseCase: GetMyInfoUseCase, private val removeTokenUseCase: RemoveTokenUseCase) : BaseViewModel() {

    var userId = 0

    private val _userName = MediatorLiveData<String>()
    val userName : LiveData<String>
        get() = _userName

    private val _logoutHolderVisible = MutableLiveData<Boolean>()
    val logoutHolderVisible : LiveData<Boolean>
        get() = _logoutHolderVisible

    private val getMyInfoResult = getMyInfoUseCase.observe()

    private val _navigateToUser = MutableLiveData<Event<Int>>()
    val navigateToUser : LiveData<Event<Int>>
        get() = _navigateToUser

    private val _navigateToFollow = MutableLiveData<Event<Unit>>()
    val navigateToFollow : LiveData<Event<Unit>>
        get() = _navigateToFollow

    private val _navigateToWrite = MutableLiveData<Event<Unit>>()
    val navigateToWrite : LiveData<Event<Unit>>
        get() = _navigateToWrite

    private val _navigateToLogin = MutableLiveData<Event<Unit>>()
    val navigateToLogin : LiveData<Event<Unit>>
        get() = _navigateToLogin

    private val _navigateToLogout = MutableLiveData<Event<Unit>>()
    val navigateToLogout : LiveData<Event<Unit>>
        get() = _navigateToLogout

    init {
        getMyInfoResult.onSuccess(_userName) {
            _userName.value = it.data.user.username
            userId = it.data.user.id
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
            _logoutHolderVisible.value = true
            this(getMyInfoUseCase(Unit))
        } else {
            _logoutHolderVisible.value = false
            _userName.value = ""
        }
    }

    fun navigateToWrite() {
        if (!TokenManager.hasToken)
            _error.value = Event("로그인이 필요한 작업입니다")
        else
            _navigateToWrite.value = Event(Unit)
    }

    fun navigateToLogin() {
        userName.value.isNullOrEmpty({
            _navigateToUser.value = Event(userId)
        },{
            _navigateToLogin.value = Event(Unit)
        })
    }

    fun navigateToFollower() {
        if (!TokenManager.hasToken)
            _error.value = Event("로그인이 필요한 작업입니다")
        else
            _navigateToFollow.value = Event(Unit)
    }

    fun navigateToLogout() {
        TokenManager.token = ""
        TokenManager.refreshToken = ""
        when (removeTokenUseCase(Unit)) {
            is Result.Success -> {
                _navigateToLogout.value = Event(Unit)        
            }
            is Result.Error -> {
                _error.value = Event("로그아웃 실패")
            }
        }
        
    }

    private fun String?.isNullOrEmpty(ok: () -> (Unit), not: () -> (Unit)) {
        this?.let {
            if (it.isNotBlank())
                ok()
            else
                not()
        } ?: run {
            not()
        }
    }
}