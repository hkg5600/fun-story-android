package com.example.fun_story.ui.feed_detail

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.domain.result.EventObserver
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityFeedDetailBinding
import com.example.presentation.BaseActivity

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedDetailActivity : BaseActivity<FeedDetailViewModel>() {

    private lateinit var binding: ActivityFeedDetailBinding
    override val viewModel: FeedDetailViewModel by viewModel()

    private lateinit var bottomSheet: BottomSheetBehavior<*>

    private val userId: Int? by lazy {
        if (intent?.getIntExtra(
                "id",
                -1
            ) != -1
        ) intent?.getIntExtra("id", -1) else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityFeedDetailBinding>(
            this,
            R.layout.activity_feed_detail
        ).apply {
            viewModel = this@FeedDetailActivity.viewModel
            lifecycleOwner = this@FeedDetailActivity
        }
        bottomSheet = BottomSheetBehavior.from(binding.menuHolder)
        binding.imageViewBack.setOnClickListener {
            finish()
        }

        binding.fabMenu.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        }

        initObserver()

        userId?.let {
            viewModel.setUerId(it)
        } ?: run {
            finish()
        }

    }

    private fun initObserver() {
        viewModel.error.observe(this, EventObserver {
            Snackbar.make(binding.holderLayout, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.feedDetail.observe(this, Observer {
            Log.e("test", it.title)
        })
    }

    override fun onBackPressed() {
        if (bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        else
            super.onBackPressed()
    }
}
