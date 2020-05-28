package com.example.fun_story.ui.save

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.result.EventObserver
import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentSaveBinding
import com.example.fun_story.BaseFragment
import com.example.fun_story.ui.feed_detail.FeedDetailActivity
import com.example.model.Feed
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class SaveFragment : BaseFragment<SaveViewModel>() {


    override val viewModel by viewModel<SaveViewModel>()
    private lateinit var binding: FragmentSaveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("onCreateView", "onCreateView Save")
        binding = DataBindingUtil.inflate<FragmentSaveBinding>(
            inflater,
            R.layout.fragment_save,
            container,
            false
        ).apply {
            viewModel = this@SaveFragment.viewModel
            lifecycleOwner = this@SaveFragment.viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.swipeLayout.setOnRefreshListener { viewModel.executeUseCase() }

        binding.recyclerviewFeed.apply {
            setHasFixedSize(true)
            adapter = SaveAdapter(viewModel)
        }

        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val id = viewHolder.adapterPosition
                    viewModel.removeItem(id)
                }
            })
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewFeed)

        initObserver()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObserver() {
        viewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver {
            startActivity(Intent(context, FeedDetailActivity::class.java).putExtra("id", it))
        })

        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                "삭제에 실패했습니다." -> Snackbar.make(binding.holderLayout, it, Snackbar.LENGTH_SHORT)
                    .show()
            }
        })

        viewModel.deleteResult.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.holderLayout, "삭제했습니다.", Snackbar.LENGTH_SHORT).show()
        })

        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            binding.swipeLayout.isRefreshing = it
        })

        viewModel.startMessage.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.holderLayout, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun onDestroy() {
        Log.e("onDestroy", "onDestroy Save")
        super.onDestroy()
    }
}

@BindingAdapter("setItem")
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

@BindingAdapter("visible")
fun setVisible(textView: TextView, shows: Boolean) {
    if (shows)
        textView.visibility = View.VISIBLE
    else
        textView.visibility = View.GONE
}
