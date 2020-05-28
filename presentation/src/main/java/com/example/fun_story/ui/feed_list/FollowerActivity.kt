package com.example.fun_story.ui.feed_list

import android.content.Intent
import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.result.EventObserver
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityFollowerBinding
import com.example.fun_story.ui.feed_detail.FeedDetailActivity
import com.example.model.FeedListData
import com.example.presentation.BaseActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowerActivity : BaseActivity<FollowerViewModel>() {

    override val viewModel: FollowerViewModel by viewModel()
    private lateinit var binding: ActivityFollowerBinding

    private val userId: Int? by lazy {
        if (intent?.getIntExtra(
                "id",
                -1
            ) != -1
        ) intent?.getIntExtra("id", -1) else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityFollowerBinding>(
            this,
            R.layout.activity_follower
        ).apply {
            viewModel = this@FollowerActivity.viewModel
            lifecycleOwner = this@FollowerActivity
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerviewFeed.apply {
            setHasFixedSize(true)
            adapter = FollowAdapter(viewModel, viewModel)
        }

        binding.swipeLayout.setOnRefreshListener {
            viewModel.executeGetFeed(0)
        }

        viewModel.isRefreshing.observe(this, Observer {
            binding.swipeLayout.isRefreshing = it
        })


        userId?.let {
            viewModel.setUserId(it)
        } ?: run {
            finish()
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.error.observe(this, EventObserver {
            when (it) {
                "network" -> Snackbar.make(
                    binding.holderLayout,
                    "네트워크 연결을 확인해주세요",
                    Snackbar.LENGTH_LONG
                ).show()
                "need token" -> Snackbar.make(
                    binding.holderLayout,
                    "로그인이 필요한 작업입니다",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })

        viewModel.navigateToDetail.observe(this, EventObserver {
            startActivity(
                Intent(this, FeedDetailActivity::class.java).putExtra("id", it)
                    .putExtra("follow", true)
            )
        })
    }

}

@BindingAdapter("followItem")
fun setItem(recyclerView: RecyclerView, feedList: FeedListData?) {
    val followAdapter: FollowAdapter
    if (recyclerView.adapter == null)
        return
    else
        followAdapter = recyclerView.adapter as FollowAdapter

    feedList?.let {
        if (it.next == 1)
            recyclerView.scrollToPosition(0)
        followAdapter.nextPage = it.next
        followAdapter.feedList = it.list
        followAdapter.notifyDataSetChanged()
        followAdapter.canLoadMore = !it.isLast
    }
}

@BindingAdapter("button_state")
fun buttonState(extendedFloatingActionButton: ExtendedFloatingActionButton, isFollowing: Boolean) {
    if (isFollowing) {
        extendedFloatingActionButton.apply {
            setIconResource(R.drawable.ic_close)
            text = "구독 취소"
        }
    } else {
        extendedFloatingActionButton.apply {
            setIconResource(R.drawable.ic_add)
            text = "구독하기"
        }
    }
}