package com.example.fun_story.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.domain.result.EventObserver
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityStartBinding
import com.example.fun_story.ui.start.join.JoinFragment
import com.example.fun_story.ui.start.login.LoginFragment
import com.example.presentation.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartActivity : BaseActivity<StartViewModel>() {

    override val viewModel: StartViewModel by viewModel()

    private lateinit var binding: ActivityStartBinding

    private val loginFragment by inject<LoginFragment>()
    private val joinFragment by inject<JoinFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityStartBinding>(this, R.layout.activity_start)
                .apply {
                    lifecycleOwner = this@StartActivity
                    viewModel = this@StartActivity.viewModel
                }

        binding.imageViewBack.setOnClickListener {
            finish()
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.navigateToJoin.observe(this, EventObserver {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentHolder.id, joinFragment).commitAllowingStateLoss()
        })

        viewModel.navigateToLogin.observe(this, EventObserver {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentHolder.id, loginFragment).commitAllowingStateLoss()
        })
    }


}
