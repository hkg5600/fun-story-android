package com.example.fun_story.ui.write.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fun_story.BaseFragment

import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentWriteCategoryBinding
import com.example.fun_story.databinding.FragmentWriteContentBinding
import com.example.fun_story.ui.feed.FeedCategoryAdapter
import com.example.fun_story.ui.write.WriteViewModel
import com.example.model.FeedCategory
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WriteCategoryFragment : BaseFragment<WriteViewModel>() {
    override val viewModel: WriteViewModel by sharedViewModel()

    private lateinit var binding : FragmentWriteCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentWriteCategoryBinding>(inflater, R.layout.fragment_write_category, container, false).apply {
            lifecycleOwner = this@WriteCategoryFragment.viewLifecycleOwner
            viewModel = this@WriteCategoryFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerviewCategory.apply {
            setHasFixedSize(true)
            adapter = FeedCategoryAdapter(viewModel, viewModel.feedCategoryList)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
