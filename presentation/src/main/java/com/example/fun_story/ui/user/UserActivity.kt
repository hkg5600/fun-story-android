package com.example.fun_story.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.result.EventObserver
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityUserBinding
import com.example.fun_story.ui.feed_detail.FeedDetailActivity
import com.example.model.FeedListData
import com.example.presentation.BaseActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserActivity : BaseActivity<UserViewModel>() {

    override val viewModel: UserViewModel by viewModel()
    private lateinit var binding: ActivityUserBinding

    private val userId: Int? by lazy {
        if (intent?.getIntExtra(
                "id",
                -1
            ) != -1
        ) intent?.getIntExtra("id", -1) else null
    }

    private val isMine: Boolean by lazy {
        intent?.getBooleanExtra("me", false) ?: false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityUserBinding>(
            this,
            R.layout.activity_user
        ).apply {
            viewModel = this@UserActivity.viewModel
            lifecycleOwner = this@UserActivity
        }

        if (isMine) {
            Snackbar.make(
                binding.holderLayout,
                "좌로 밀어 삭제",
                Snackbar.LENGTH_LONG
            ).show()
            binding.buttonFollow.visibility = View.GONE
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
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerviewFeed.apply {
            setHasFixedSize(true)
            adapter = UserAdapter(viewModel, viewModel)
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
                "삭제 실패" -> Snackbar.make(
                    binding.holderLayout, it, Snackbar.LENGTH_LONG
                ).show()
                else -> {
                    Snackbar.make(
                        binding.holderLayout, it, Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        viewModel.navigateToDetail.observe(this, EventObserver {
            startActivity(
                Intent(this, FeedDetailActivity::class.java).putExtra("id", it)
                    .putExtra("follow", true)
            )
        })

        viewModel.followResult.observe(this, Observer {
            Snackbar.make(binding.holderLayout, it, Snackbar.LENGTH_LONG).show()
        })
    }

}

@BindingAdapter("followItem")
fun setItem(recyclerView: RecyclerView, feedList: FeedListData?) {
    val userAdapter: UserAdapter
    if (recyclerView.adapter == null)
        return
    else
        userAdapter = recyclerView.adapter as UserAdapter

    feedList?.let {
        if (it.next == 1)
            recyclerView.scrollToPosition(0)
        userAdapter.nextPage = it.next
        userAdapter.feedList = it.list
        userAdapter.notifyDataSetChanged()
        userAdapter.canLoadMore = !it.isLast
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