package com.example.fun_story.ui.save

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentSaveBinding
import com.example.fun_story.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SaveFragment : BaseFragment<SaveViewModel>() {


    override val viewModel by viewModel<SaveViewModel>()
    private lateinit var binding: FragmentSaveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("onCreateView", "onCreateView Save")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_save, container, false)
        return binding.root
    }

    override fun onDestroy() {
        Log.e("onDestroy", "onDestroy Save")
        super.onDestroy()
    }

}
