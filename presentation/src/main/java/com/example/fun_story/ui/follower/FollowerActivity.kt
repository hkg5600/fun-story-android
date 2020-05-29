package com.example.fun_story.ui.follower

import android.content.Intent
import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.result.EventObserver
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityFollowerBinding
import com.example.fun_story.ui.user.UserActivity
import com.example.model.FollowerData
import com.example.presentation.BaseActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowerActivity : BaseActivity<FollowerViewModel>() {

    override val viewModel: FollowerViewModel by viewModel()
    private lateinit var binding: ActivityFollowerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityFollowerBinding>(
            this,
            R.layout.activity_follower
        ).apply {
            lifecycleOwner = this@FollowerActivity
            viewModel = this@FollowerActivity.viewModel
        }

        binding.recyclerviewUser.apply {
            adapter = FollowerAdapter(viewModel)
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.error.observe(this, EventObserver {
            Snackbar.make(binding.holderLayout, it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.navigateToUser.observe(this, EventObserver {
            startActivity(
                Intent(this, UserActivity::class.java).putExtra("id", it)
            )
        })
    }

}

@BindingAdapter("setUserItem")
fun setUserItem(recyclerView: RecyclerView, followerData: FollowerData?) {

    val followerAdapter: FollowerAdapter

    if (recyclerView.adapter == null) {
        return
    } else {
        followerAdapter = recyclerView.adapter as FollowerAdapter
    }

    followerData?.let {
        followerAdapter.followerList = it.list
        followerAdapter.nextPage = followerData.next
        followerAdapter.notifyDataSetChanged()
        followerAdapter.canLoadMore = !followerData.isLast
    }


}