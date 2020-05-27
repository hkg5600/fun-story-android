package com.example.fun_story.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.domain.result.EventObserver
import com.example.fun_story.R
import com.example.fun_story.databinding.FragmentFeedBinding
import com.example.fun_story.ui.feed_detail.FeedDetailActivity
import com.example.model.FeedListData
import com.example.fun_story.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FeedViewModel>() {

    override val viewModel by viewModel<FeedViewModel>()
    private lateinit var binding: FragmentFeedBinding

    private lateinit var bottomSheet: BottomSheetBehavior<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFeedBinding>(
            inflater,
            R.layout.fragment_feed,
            container,
            false
        ).apply {
            viewModel = this@FeedFragment.viewModel
            lifecycleOwner = this@FeedFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bottomSheet = BottomSheetBehavior.from(binding.filterHolder)

        binding.recyclerviewFeed.apply {
            adapter =  FeedAdapter(viewModel)
        }

        binding.recyclerviewCategory.apply {
            setHasFixedSize(true)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = FeedFilterAdapter(viewModel, viewModel.feedCategoryList)
        }

        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if (p1 == p0?.endState) {
                    viewModel.changeMotionLayoutState(true)
                    bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        })

        binding.switchFollow.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleSwitch(isChecked)
        }

        binding.imageViewClose.setOnClickListener {
            closeBottomSheet()
        }

        Snackbar.make(binding.holderLayout, "스와이프하여 피드를 새로고침", Snackbar.LENGTH_SHORT).show()
        initObserver()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObserver() {
        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                "network" -> Snackbar.make(
                    binding.holderLayout,
                    "피드를 새로 고침할 수 없음",
                    Snackbar.LENGTH_SHORT
                ).show()
                "로그인이 필요한 기능입니다" -> {
                    binding.switchFollow.isChecked = false
                    Snackbar.make(binding.holderLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver {
            startActivity(Intent(context, FeedDetailActivity::class.java).putExtra("id", it))
        })
    }

    //callback
    fun onBackPressed(): Boolean {
        return if (bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED) {
            closeBottomSheet()
            true
        } else {
            false
        }

    }

    private fun closeBottomSheet() {
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}

@BindingAdapter("selectedItem")
fun selectedItem(recyclerView: RecyclerView, category: FeedCategory?) {
    val feedFilterAdapter: FeedFilterAdapter

    if (recyclerView.adapter == null)
        return
    else
        feedFilterAdapter = recyclerView.adapter as FeedFilterAdapter

    feedFilterAdapter.selectedItem = category
    feedFilterAdapter.notifyDataSetChanged()

}

@BindingAdapter("item")
fun setItem(recyclerView: RecyclerView, feedList: FeedListData?) {
    val feedAdapter: FeedAdapter
    if (recyclerView.adapter == null)
        return
    else
        feedAdapter = recyclerView.adapter as FeedAdapter

    feedList?.let {
        if (it.next == 1)
            recyclerView.scrollToPosition(0)
        feedAdapter.nextPage = it.next
        feedAdapter.feedList = it.list
        feedAdapter.notifyDataSetChanged()
        feedAdapter.canLoadMore = !it.isLast
    }
}

@BindingAdapter("onRefresh")
fun setOnRefreshListener(swipeRefreshLayout: SwipeRefreshLayout, viewModel: FeedViewModel) {
    swipeRefreshLayout.setOnRefreshListener {
        swipeRefreshLayout.isRefreshing = false
        viewModel.executeGetFeed(0)
    }
}

@BindingAdapter("state")
fun changeMotionLayoutState(motionLayout: MotionLayout, boolean: Boolean) {
    if (boolean)
        motionLayout.transitionToStart()
    else
        motionLayout.transitionToEnd()
}

