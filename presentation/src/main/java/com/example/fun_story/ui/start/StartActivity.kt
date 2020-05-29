package com.example.fun_story.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityStartBinding
import com.example.presentation.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartActivity : BaseActivity<StartViewModel>() {

    override val viewModel: StartViewModel by viewModel()

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityStartBinding>(this, R.layout.activity_start)
                .apply {
                    lifecycleOwner = this@StartActivity
                    viewModel = this@StartActivity.viewModel
                }


    }


}
