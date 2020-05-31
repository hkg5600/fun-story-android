package com.example.fun_story.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.domain.result.EventObserver
import com.example.fun_story.ui.main.MainActivity
import com.example.fun_story.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.complete.observe(this, EventObserver {
            startMain()
        })

        viewModel.loginAgain.observe(this, EventObserver {
            Toast.makeText(this, "인증에 실패했습니다", Toast.LENGTH_LONG).show()
            startMain()
        })

        viewModel.error.observe(this, EventObserver{
            when (it) {
                "network" -> {
                    Toast.makeText(this, "인증에 실패했습니다", Toast.LENGTH_LONG).show()
                    startMain()
                }
            }
        })
    }

    private fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
