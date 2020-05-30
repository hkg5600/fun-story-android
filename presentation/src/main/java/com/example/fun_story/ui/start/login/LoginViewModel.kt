package com.example.fun_story.ui.start.login

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Event
import com.example.domain.token.TokenManager
import com.example.domain.user.LoginUseCase
import com.example.fun_story.BaseViewModel
import com.example.model.LoginParameter

class LoginViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel() {
    val username = ObservableField<String>("")
    val password = ObservableField<String>("")

    private val _completeLogin = MediatorLiveData<Event<Unit>>()
    val completeLogin : LiveData<Event<Unit>>
        get() = _completeLogin

    private val _enabled = MutableLiveData<Boolean>(false)
    val enabled : LiveData<Boolean>
        get() = _enabled

    init {
        loginUseCase.observe().onSuccess(_completeLogin) {
            val token = it.data.token
            val refresh = it.data.refresh
            if (token == null || refresh == null) {
                _error.value = Event("예상치 못한 오류 발생")
                return@onSuccess
            }
            TokenManager.token = token
            TokenManager.refreshToken = refresh
            _completeLogin.value = Event(Unit)
        }

        loginUseCase.observe().onError(_error) {
            _error.value = Event(it)
        }

    }

    private fun createLoginParameter(): LoginParameter {
        return LoginParameter(username.get()!!, password.get()!!)
    }

    fun login() {
        this(loginUseCase(createLoginParameter()))
    }

    fun setButtonEnabled(enabled: Boolean) {
        _enabled.value = enabled
    }

    override fun onCleared() {
        Log.e("Login", "onCleared")
        super.onCleared()
    }
}