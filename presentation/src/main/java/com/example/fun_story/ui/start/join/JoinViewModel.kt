package com.example.fun_story.ui.start.join

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Event
import com.example.domain.token.TokenManager
import com.example.domain.user.JoinUseCase
import com.example.fun_story.BaseViewModel
import com.example.model.JoinParameter

class JoinViewModel(private val joinUseCase: JoinUseCase) : BaseViewModel() {
    val username = ObservableField<String>("")
    val password1 = ObservableField<String>("")
    val password2 = ObservableField<String>("")

    private val _completeJoin = MediatorLiveData<Event<Unit>>()
    val completeJoin : LiveData<Event<Unit>>
        get() = _completeJoin

    private val _enabled = MutableLiveData<Boolean>(false)
    val enabled : LiveData<Boolean>
        get() = _enabled

    init {
        joinUseCase.observe().onSuccess(_completeJoin) {
            val token = it.data.token
            val refresh = it.data.refresh
            if (token == null || refresh == null) {
                _error.value = Event("예상치 못한 오류 발생")
                return@onSuccess
            }
            TokenManager.token = token
            TokenManager.refreshToken = refresh
            _completeJoin.value = Event(Unit)
        }

        joinUseCase.observe().onError(_error) {
            _error.value = Event(it)
        }

    }

    private fun createJoinParameter(): JoinParameter {
        return JoinParameter(username.get()!!, password1.get()!!, password2.get()!!)
    }

    fun join() {
        this(joinUseCase(createJoinParameter()))
    }

    fun setButtonEnabled(enabled: Boolean) {
        _enabled.value = enabled
    }
}