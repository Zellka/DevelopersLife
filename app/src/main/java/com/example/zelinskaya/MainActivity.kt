package com.example.zelinskaya

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.zelinskaya.adapter.PagerAdapter
import com.example.zelinskaya.databinding.ActivityMainBinding
import io.paperdb.Paper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Paper.init(this)

        binding.viewPager.adapter = PagerAdapter(this, supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }


}