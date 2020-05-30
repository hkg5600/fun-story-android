package com.example.fun_story.ui.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityWriteBinding
import com.example.presentation.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteActivity : BaseActivity<WriteViewModel>() {

    override val viewModel: WriteViewModel by viewModel()

    private lateinit var binding : ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityWriteBinding>(this, R.layout.activity_write).apply {
            lifecycleOwner = this@WriteActivity
            viewModel = this@WriteActivity.viewModel
        }
    }

}
