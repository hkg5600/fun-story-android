package com.example.fun_story.ui.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fun_story.DetailNavigator
import com.example.fun_story.databinding.FeedItemBinding
import com.example.model.Feed

class FollowAdapter(private val viewModel: FollowerViewModel, private val navigator: DetailNavigator) :
    RecyclerView.Adapter<FollowAdapter.FeedHolder>() {
    class FeedHolder(private val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Feed) {
            binding.item = item
        }
    }

    var nextPage = 0
    var canLoadMore = true
    var feedList = ArrayList<Feed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FeedHolder(
        FeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            navigate = this@FollowAdapter.navigator
        }
    )

    override fun getItemCount() = feedList.size

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        if (canLoadMore && position >= feedList.size - 1) {
            viewModel.executeGetFeed(nextPage)
        }

        holder.bind(feedList[position])
    }
}