package com.example.fun_story.ui.feed_detail

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import com.example.domain.result.EventObserver
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityFeedDetailBinding
import com.example.fun_story.ui.user.UserActivity
import com.example.presentation.BaseActivity

import com.google.android.material.bottomsheet.BottomSheetBehavior
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

    private val fromFollow: Boolean by lazy {
        intent.getBooleanExtra("follow", false)
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

        binding.textViewUser.setOnClickListener {
            if (!viewModel.getNetworkState()) {
                viewModel.raiseNetworkError()
                return@setOnClickListener
            }
            if (fromFollow)
                finish()
            else
                startActivity(
                    Intent(this, UserActivity::class.java).putExtra(
                        "id",
                        viewModel.feedData.value?.user
                    )
                )
        }

        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if (p1 == p0?.endState) {
                    binding.motionLayout.transitionToStart()
                    bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        })

        initObserver()

        userId?.let {
            viewModel.setUserId(it)
        } ?: run {
            finish()
        }

    }

    private fun initObserver() {
        viewModel.error.observe(this, EventObserver {
            when (it) {
                "이야기를 불러오는데 실패했습니다\n" +
                        "다시 시도해주세요" -> makeToast(it, false)
                else -> makeToast("저장에 실패했습니다.", false)
            }

        })

        viewModel.saveResult.observe(this, EventObserver {
            if (!it) {
                makeToast("저장에 실패했습니다.", false)
            } else {
                makeToast("보관된 이야기는 [보관함]에서 확인할 수 있습니다.", true)
            }
        })

    }

    override fun onBackPressed() {
        if (bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        else
            super.onBackPressed()
    }
}
