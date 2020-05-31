package com.example.fun_story.ui.write.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.example.fun_story.BaseFragment

import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentWriteContentBinding
import com.example.fun_story.ui.write.WriteViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WriteContentFragment : BaseFragment<WriteViewModel>() {
    override val viewModel: WriteViewModel by sharedViewModel()

    private lateinit var binding : FragmentWriteContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentWriteContentBinding>(inflater, R.layout.fragment_write_content, container, false).apply {
            lifecycleOwner = this@WriteContentFragment.viewLifecycleOwner
            viewModel = this@WriteContentFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initObserver()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObserver() {
        binding.editTextTitle.doOnTextChanged { _, _, _, _ ->
            setButtonEnabled()
        }

        binding.editTextDescription.doOnTextChanged { _, _, _, _ ->
            setButtonEnabled()
        }

    }

    private fun setButtonEnabled() {
        val enabled = binding.editTextTitle.text.isNotBlank() && binding.editTextDescription.text.isNotBlank()
        viewModel.setFinishEnable(enabled)
    }
}
