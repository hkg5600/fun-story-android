package com.example.fun_story.ui.start.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.example.domain.result.EventObserver
import com.example.fun_story.BaseFragment

import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentJoinBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class JoinFragment : BaseFragment<JoinViewModel>() {
    private lateinit var binding: FragmentJoinBinding
    override val viewModel: JoinViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentJoinBinding>(
            inflater,
            R.layout.fragment_join,
            container,
            false
        ).apply {
            viewModel = this@JoinFragment.viewModel
            lifecycleOwner = this@JoinFragment.viewLifecycleOwner
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

        viewModel.completeJoin.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.holderLayout, "회원가입 성공", Snackbar.LENGTH_LONG).show()
            activity?.finish()
        })

        binding.editTextId.doOnTextChanged { _, _, _, _ ->
            setButtonEnabled()
        }

        binding.editTextPassword.doOnTextChanged { _, _, _, _ ->
            setButtonEnabled()
            checkEquals()
        }

        binding.editTextPasswordAgain.doOnTextChanged { _, _, _, _ ->
            setButtonEnabled()
            checkEquals()
        }
    }

    private fun setButtonEnabled() {
        val enabled =
            binding.editTextId.text.isNotBlank() && binding.editTextPassword.text.isNotBlank()
        viewModel.setButtonEnabled(enabled)
    }

    private fun checkEquals() {
        if (binding.editTextPassword.text.toString() == binding.editTextPasswordAgain.text.toString()) {
            binding.editTextPasswordAgain.error = null
        } else {
            binding.editTextPasswordAgain.error = "비밀번호가 일치하지 않습니다"
        }
    }

}
