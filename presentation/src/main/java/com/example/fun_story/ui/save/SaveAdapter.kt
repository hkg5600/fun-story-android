package com.example.fun_story.ui.save

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fun_story.DetailNavigator
import com.example.fun_story.databinding.FeedItemBinding
import com.example.model.Feed

class SaveAdapter(private val navigator: DetailNavigator) :
    RecyclerView.Adapter<SaveAdapter.SaveHolder>() {

    class SaveHolder(private val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Feed) {
            binding.item = item
        }
    }

    var feedList = ArrayList<Feed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SaveHolder(
        FeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            navigate = this@SaveAdapter.navigator
        }
    )

    override fun getItemCount() = feedList.size

    override fun onBindViewHolder(holder: SaveHolder, position: Int) {
        holder.bind(feedList[position])
    }
}