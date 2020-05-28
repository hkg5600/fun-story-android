package com.example.fun_story.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.result.Event
import com.example.domain.result.Result
import com.example.domain.token.GetTokenUseCase
import com.example.domain.token.RefreshTokenUseCase
import com.example.domain.token.TokenManager
import com.example.domain.token.VerifyTokenUserCase
import com.example.fun_story.BaseViewModel
class SplashViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val verifyTokenUserCase: VerifyTokenUserCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
) : BaseViewModel() {


    private val _complete = MediatorLiveData<Event<Unit>>()
    val complete: LiveData<Event<Unit>>
        get() = _complete

    private val verifyTokenResult = verifyTokenUserCase.observe()
    private val refreshTokenResult = refreshTokenUseCase.observe()

    init {
        when (val it = getTokenUseCase(Unit)) {
            is Result.Success -> {
                TokenManager.token = it.data.first
                TokenManager.refreshToken = it.data.second
                this(verifyTokenUserCase(TokenManager.token))
            }
            is Result.Error -> {
                _complete.value = Event(Unit)
            }
        }

        verifyTokenResult.onSuccess(_complete) {
            _complete.value = Event(Unit)
        }

        verifyTokenResult.onError(_error) {
            when (it) {
                "만료된 토큰입니다" -> this(refreshTokenUseCase(TokenManager.refreshToken))
                "조작된 토큰입니다" -> {
                    TokenManager.token = ""
                    TokenManager.refreshToken = ""
                    _loginAgain.value = Event(Unit)
                }
            }
        }

        refreshTokenResult.onSuccess(_complete) {
            TokenManager.token = it.data.token!!
            TokenManager.refreshToken = it.data.refresh!!
            _complete.value = Event(Unit)
        }

        refreshTokenResult.onError(_error) {
            TokenManager.token = ""
            TokenManager.refreshToken = ""
            when (it) {
                "조작된 토큰입니다","만료된 리프레쉬 토큰입니다" -> _loginAgain.value = Event(Unit)
            }
        }

    }
}