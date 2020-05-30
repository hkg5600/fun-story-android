package com.example.fun_story.ui.start.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.example.domain.result.EventObserver
import com.example.fun_story.BaseFragment

import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModel()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        ).apply {
            viewModel = this@LoginFragment.viewModel
            lifecycleOwner = this@LoginFragment.viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                else -> Snackbar.make(binding.holderLayout, it, Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.completeLogin.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.holderLayout, "로그인 성공", Snackbar.LENGTH_LONG).show()
            activity?.finish()
        })

        binding.editTextId.doOnTextChanged { _, _, _, _ ->
            setButtonEnabled()
        }

        binding.editTextPassword.doOnTextChanged { _, _, _, _ ->
            setButtonEnabled()
        }
    }

    private fun setButtonEnabled() {
        val enabled = binding.editTextId.text.isNotBlank() && binding.editTextPassword.text.isNotBlank()
        viewModel.setButtonEnabled(enabled)
    }
}