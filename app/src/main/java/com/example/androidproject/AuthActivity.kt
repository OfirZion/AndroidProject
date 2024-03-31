package com.example.androidproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidproject.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }


}