package com.example.fun_story.ui.write

import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityWriteBinding
import com.example.fun_story.ui.write.category.WriteCategoryFragment
import com.example.fun_story.ui.write.content.WriteContentFragment
import com.example.presentation.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteActivity : BaseActivity<WriteViewModel>() {

    override val viewModel: WriteViewModel by viewModel()

    private lateinit var binding : ActivityWriteBinding

    private val writeContentFragment by inject<WriteContentFragment>()
    private val writeCategoryFragment by inject<WriteCategoryFragment>()

    private var transaction = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityWriteBinding>(this, R.layout.activity_write).apply {
            lifecycleOwner = this@WriteActivity
            viewModel = this@WriteActivity.viewModel
        }

        binding.toolbar.menu[0].setOnMenuItemClickListener {

            true
        }

        binding.toolbar.menu[1].setOnMenuItemClickListener {
            viewModel.setPage(true)
            true
        }

        binding.toolbar.setNavigationOnClickListener {
            if (viewModel.pageState.value == true) {
                finish()
            } else {
                viewModel.setPage(false)
            }
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.finishWriteEnable.observe(this, Observer {
            binding.toolbar.menu[0].isEnabled = it
        })

        viewModel.finishWriteVisible.observe(this, Observer {
            binding.toolbar.menu[0].isVisible = it
        })

        viewModel.nextMenuEnable.observe(this, Observer {
            binding.toolbar.menu[1].isEnabled = it
        })

        viewModel.nextMenuVisible.observe(this, Observer {
            binding.toolbar.menu[1].isVisible = it
        })

        viewModel.pageState.observe(this, Observer {
            transaction = supportFragmentManager.beginTransaction()
            Log.e("TEST", viewModel.selectedCategory.value?.categoryName ?: "Null")
            if (it)
                showFragment(writeCategoryFragment)
            else
                showFragment(writeContentFragment)
        })
    }

    private fun showFragment(fragment: Fragment) {
        transaction.replace(binding.fragmentHolder.id, fragment).commitAllowingStateLoss()
    }

}
