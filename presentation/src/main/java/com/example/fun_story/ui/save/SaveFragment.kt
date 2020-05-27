package com.example.fun_story.ui.save

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.result.EventObserver
import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentSaveBinding
import com.example.fun_story.BaseFragment
import com.example.fun_story.ui.feed_detail.FeedDetailActivity
import com.example.model.Feed
import org.koin.androidx.viewmodel.ext.android.viewModel


class SaveFragment : BaseFragment<SaveViewModel>() {


    override val viewModel by viewModel<SaveViewModel>()
    private lateinit var binding: FragmentSaveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("onCreateView", "onCreateView Save")
        binding = DataBindingUtil.inflate<FragmentSaveBinding>(inflater, R.layout.fragment_save, container, false).apply {
            viewModel = this@SaveFragment.viewModel
            lifecycleOwner = this@SaveFragment.viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.executeUseCase(true)
        }
        binding.recyclerviewFeed.apply {
            setHasFixedSize(true)
            adapter = SaveAdapter(viewModel)
        }
        initObserver()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObserver() {
        viewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver{
            startActivity(Intent(context, FeedDetailActivity::class.java).putExtra("id", it))
        })

        viewModel.error.observe(viewLifecycleOwner, EventObserver {

        })
    }

    override fun onDestroy() {
        Log.e("onDestroy", "onDestroy Save")
        super.onDestroy()
    }
}

@BindingAdapter("seteItem")
fun setItem(recyclerView: RecyclerView, item: ArrayList<Feed>?) {
    val saveAdapter: SaveAdapter
    if (recyclerView.adapter == null)
        return
    else
        saveAdapter = recyclerView.adapter as SaveAdapter

    item?.let {
        saveAdapter.feedList = it
        saveAdapter.notifyDataSetChanged()
    }

}
