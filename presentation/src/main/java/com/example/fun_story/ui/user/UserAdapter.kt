package com.example.fun_story.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fun_story.DetailNavigator
import com.example.fun_story.databinding.FeedItemBinding
import com.example.model.Feed

class UserAdapter(private val viewModel: UserViewModel, private val navigator: DetailNavigator) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {
    class UserHolder(private val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Feed) {
            binding.item = item
        }
    }

    var nextPage = 0
    var canLoadMore = true
    var feedList = ArrayList<Feed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserHolder(
        FeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            navigate = this@UserAdapter.navigator
        }
    )

    override fun getItemCount() = feedList.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        if (canLoadMore && position >= feedList.size - 1) {
            viewModel.executeGetFeed(nextPage)
        }

        holder.bind(feedList[position])
    }
}