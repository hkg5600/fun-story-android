package com.example.fun_story.ui.save

import android.util.Log
import com.example.fun_story.BaseViewModel

class SaveViewModel : BaseViewModel() {

    override fun onCleared() {
        Log.e("ViewModel", "Save")
        super.onCleared()
    }
}