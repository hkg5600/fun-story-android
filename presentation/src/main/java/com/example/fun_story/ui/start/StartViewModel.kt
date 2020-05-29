package com.example.fun_story.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Event
import com.example.fun_story.BaseViewModel

class StartViewModel : BaseViewModel() {

    private var isLoginFragment: Boolean = true

    private val _navigateToLogin = MutableLiveData<Event<Unit>>()
    val navigateToLogin: LiveData<Event<Unit>>
        get() = _navigateToLogin

    private val _navigateToJoin = MutableLiveData<Event<Unit>>()
    val navigateToJoin: LiveData<Event<Unit>>
        get() = _navigateToJoin

    val textViewText = MutableLiveData("회원가입 페이지로")

    init {
        _navigateToLogin.value = Event(Unit)
    }

    fun navigate() {
        if (!isLoginFragment) {
            textViewText.value = "회원가입 페이지로"
            _navigateToLogin.value = Event(Unit)
        } else {
            textViewText.value = "로그인 페이지로"
            _navigateToJoin.value = Event(Unit)
        }
        isLoginFragment = !isLoginFragment
    }
}