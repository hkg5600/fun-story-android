package com.example.fun_story.ui.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fun_story.databinding.UserItemBinding
import com.example.model.FollowerData
import com.example.model.User

class FollowerAdapter(val viewModel: FollowerViewModel) :
    RecyclerView.Adapter<FollowerAdapter.FollowerHolder>() {

    class FollowerHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.item = item
        }
    }

    var followerList = ArrayList<User>()
    var nextPage = 0
    var canLoadMore = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FollowerHolder(UserItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).apply { viewModel = this@FollowerAdapter.viewModel })

    override fun getItemCount() = followerList.size

    override fun onBindViewHolder(holder: FollowerHolder, position: Int) {
        if (canLoadMore && position >= followerList.size - 1)
            viewModel.executeGetFollower(nextPage)
        holder.bind(followerList[position])
    }
}