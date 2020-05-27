package com.example.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
abstract class BaseActivity<VM> : AppCompatActivity() {

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun makeToast(text: String, isLong: Boolean) {
        Toast.makeText(this, text, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }
}