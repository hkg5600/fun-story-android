package com.example.fun_story

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.domain.result.EventObserver

abstract class BaseFragment<VM> : Fragment() {
    abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (viewModel as BaseViewModel).loginAgain.observe(viewLifecycleOwner, EventObserver {
            makeToast("인증에 실패했습니다.", true)
        })

        super.onViewCreated(view, savedInstanceState)
    }

    fun makeToast(text: String, isLong: Boolean) {
        Toast.makeText(context, text, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }
}