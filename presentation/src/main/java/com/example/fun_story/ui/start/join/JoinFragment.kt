package com.example.fun_story.ui.start.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fun_story.BaseFragment

import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentJoinBinding
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

        super.onViewCreated(view, savedInstanceState)
    }


}
