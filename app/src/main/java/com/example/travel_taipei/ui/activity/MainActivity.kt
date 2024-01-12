package com.example.travel_taipei.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_taipei.databinding.ActivityMainBinding
import com.example.travel_taipei.util.initAppLanguage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mtAppbar)
        initAppLanguage(this)
    }
}