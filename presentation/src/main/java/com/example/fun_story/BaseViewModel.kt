package com.example.fun_story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.example.domain.result.Result
import com.example.domain.result.Event

abstract class BaseViewModel() : ViewModel() {
    private val disposable = CompositeDisposable()

    protected val _loginAgain = MutableLiveData<Event<Unit>>()
    val loginAgain: LiveData<Event<Unit>>
        get() = _loginAgain

    protected val _error = MediatorLiveData<Event<String>>()
    val error: LiveData<Event<String>>
        get() = _error

    operator fun invoke(disposable: Disposable) {
        this.disposable.add(disposable)
    }

    fun <T, R> MutableLiveData<Result<R>>.onSuccess(
        observer: MediatorLiveData<T>,
        onSuccess: (result: Result.Success<R>) -> Unit
    ) {
        observer.addSource(this) {
            if (it is Result.Success) {
                onSuccess(it)
            }
        }
    }

    fun <T, R> MutableLiveData<Result<R>>.onError(
        observer: MediatorLiveData<Event<T>>,
        onError: (result: String) -> Unit
    ) {
        observer.addSource(this) {
            if (it is Result.Error) {
                checkErrorType(it.errorMsg, onError)
            }
        }
    }

    private fun checkErrorType(errorMsg: String, onError: (result: String) -> Unit) {
        Log.e("Error Type", errorMsg)
        when (errorMsg) {
            "token" -> {
                _loginAgain.value = Event(Unit)
            }
            else -> onError(errorMsg)
        }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

}