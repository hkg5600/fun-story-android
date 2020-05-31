package com.example.fun_story.ui.feed

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fun_story.CategorySelector
import com.example.fun_story.R
import com.example.fun_story.databinding.FilterItemBinding
import com.example.model.FeedCategory

class FeedCategoryAdapter(
    private val selector: CategorySelector,
    private val categoryList: ArrayList<FeedCategory>
) : RecyclerView.Adapter<FeedCategoryAdapter.FeedFilterHolder>() {

    var selectedItem: FeedCategory? = null

    class FeedFilterHolder(private val binding: FilterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val holderLayout = binding.holderLayout
        val textView = binding.textViewCategoryName

        fun bind(item: FeedCategory) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FeedFilterHolder(
        FilterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).apply { selector = this@FeedCategoryAdapter.selector }
    )

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: FeedFilterHolder, position: Int) {

        selectedItem?.let {
            if (it == categoryList[position]) {
                toggleState(holder, true)
            } else {
                toggleState(holder, false)
            }
        } ?: run {
            toggleState(holder, false)
        }

        holder.bind(categoryList[position])
    }

    private fun toggleState(holder: FeedFilterHolder, isSelected: Boolean) {
        if (isSelected) {
            holder.holderLayout.backgroundTintList = ColorStateList.valueOf(R.attr.colorPrimary)
            holder.textView.setTextColor(Color.WHITE)
        } else {
            holder.holderLayout.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            holder.textView.setTextColor(Color.BLACK)
        }
    }

}