package com.example.fun_story.ui.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.example.domain.result.EventObserver
import com.example.domain.token.TokenManager
import com.example.fun_story.BaseFragment

import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentInfoBinding
import com.example.fun_story.ui.user.UserActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : BaseFragment<InfoViewModel>() {

    override val viewModel: InfoViewModel by viewModel()

    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentInfoBinding>(
            inflater,
            R.layout.fragment_info,
            container,
            false
        ).apply {
            viewModel = this@InfoFragment.viewModel
            lifecycleOwner = this@InfoFragment.viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.initLoginState()

        binding.logoutHolder.visibility = if (TokenManager.hasToken) View.VISIBLE else View.GONE
        initObserver()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObserver() {
        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                "network" -> Snackbar.make(
                    binding.holderLayout,
                    "네트워크 연결을 확인해주세요",
                    Snackbar.LENGTH_LONG
                ).show()
                "알 수 없는 오류 발생" -> Snackbar.make(binding.holderLayout, it, Snackbar.LENGTH_LONG)
                    .show()
            }
        })

        viewModel.navigateToInfo.observe(viewLifecycleOwner, EventObserver {
            startActivity(Intent(context, UserActivity::class.java).putExtra("id", it).putExtra("me", true))
        })

    }
}


@BindingAdapter("state")
fun loginState(textView: TextView, username: String?) {
    username?.let {
        if (it.isBlank()) {
            textView.text = "로그인"
        } else {
            textView.text = username
        }
    } ?: run {
        textView.text = "로그인"
    }

}