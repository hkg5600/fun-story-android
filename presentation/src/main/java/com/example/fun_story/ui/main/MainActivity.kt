package com.example.fun_story.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fun_story.R
import com.example.fun_story.databinding.ActivityMainBinding
import com.example.fun_story.ui.feed.FeedFragment
import com.example.presentation.BaseActivity
import com.example.fun_story.ui.save.SaveFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var binding: ActivityMainBinding

    override val viewModel: MainViewModel by viewModel()

    private var transaction = supportFragmentManager.beginTransaction()

    private val feedFragment by inject<FeedFragment>()
    private val saveFragment by inject<SaveFragment>()

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNav.setOnNavigationItemSelectedListener {
            transaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.menu_list -> showFragment(feedFragment)
                R.id.menu_save -> showFragment(saveFragment)
            }
            true
        }

        showFragment(feedFragment)

        initObserver()
    }

    private fun initObserver() {

    }

    private fun showFragment(fragment: Fragment) {
        hideCurrentFragment()
        if (fragment.isAdded) transaction.show(fragment)
        else supportFragmentManager.beginTransaction().add(binding.fragmentHolder.id, fragment)
            .show(fragment).commitAllowingStateLoss()
        currentFragment = fragment
    }

    private fun hideCurrentFragment() {
        currentFragment?.let {
            transaction.hide(it).commitAllowingStateLoss()
        }
    }

    override fun onBackPressed() {
        if (currentFragment == feedFragment) {
            if(!feedFragment.onBackPressed()) super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

}
